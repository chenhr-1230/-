// package com.mrbeanc.controller;

// import com.mrbeanc.model.Clipboard;
// import com.mrbeanc.util.Utils;
// import jakarta.annotation.Resource;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.context.request.async.DeferredResult;

// import java.time.Duration;
// import java.time.Instant;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.concurrent.atomic.AtomicBoolean;

// @RestController
// @Slf4j
// public class ClipboardController {
//     @Value("${clipboard.expire.minutes}")
//     private int EXPIRE_MINUTES;

//     @Resource(name = "clipboards")
//     private Map<String, Clipboard> clips;
//     private final Map<String, DeferredResult<Clipboard>> waitlist = new ConcurrentHashMap<>();

//     /**长轮询，如果是HOOK Ctrl+V再去获取的话，可能会有延时，体验很差<br>
//      * 若服务端异常掉线重启，客户端应超时重连，重新进入这个方法，重新注册一次，所以不用担心push失败 <br>
//      * 如果配置了Nginx反向代理，需要配置`proxy_read_timeout`大于长轮询超时时间，否则会返回504 <br>
//      * 这个read指的Nginx是向upstream（也就是后端服务器）读取数据的超时时间 <br>
//      */
//     @GetMapping("/clipboard/long-polling/{id}/{os}") //for Windows
//     public DeferredResult<Clipboard> clipboard_long_polling(@PathVariable String id, @PathVariable String os) {
//         DeferredResult<Clipboard> deferResult = new DeferredResult<>(60_000L); //60s
//         // 包含所有完成状态（正常、超时、异常），集中处理
//         // 这对于检测 DeferredResult 实例不再可用非常有用
//         deferResult.onCompletion(() -> { // 完成后从waitlist中移除
//             /* 若id存在，且是该对象，则remove
//              * 相较于手动check-then-act，该函数可以确保线程安全，防止并发修改
//              * better than:
//              * if (map.containsKey(key) && Objects.equals(map.get(key), value))
//              *     map.remove(key);
//              */
//             waitlist.remove(id, deferResult);
//             // 超时后若setErrorResult，则不会抛出异常，但是会返回状态码200，不符合预期
//             // 故不设置errorResult，而是在全局异常处理中捕获AsyncRequestTimeoutException，返回状态码304
//         });

//         //如果不消费(remove)数据，就无法判断是否新数据，推送可能重复或失败（轮询间隔时更新）
//         //因此目前只支持 单生产者 单消费者（符合日常使用）
//         AtomicBoolean hasData = new AtomicBoolean(false);
//         clips.computeIfPresent(id, (k, v) -> {
//             if (!v.getOs().equals(os)) { // 不能消费自己推送的数据
//                 deferResult.setResult(v); // 直接响应数据
//                 hasData.set(true);
//                 return null; // null to `remove` k-v mapping，消费并移除数据
//             }
//             return v;
//         });

//         if (!hasData.get()) {
//             waitlist.compute(id, (k, v) -> {
//                 if (v != null) {
//                     v.setErrorResult("肿么会同时发起多个长轮询！！不过有可能是客户端掉线or超时重连maybe");
//                     log.warn("长江后浪推前浪！！");
//                     // 有可能是服务端重启，客户端单个long-polling自动重连，重新进入这个方法
//                     // 但是，从客户端来看是一个请求之内，继续计时，超时时间为90s
//                     // 从服务端来看，是从0开始计时，还没到60s，客户端就超过90s了，导致客户端主动abort，服务端不知道，继续等在waitlist中
//                     // 此时客户端又发起请求，导致double long-polling
//                 }
//                 return deferResult; // 保存到waitlist
//             });
//         }

//         return deferResult;
//     }

//     @GetMapping("/clipboard/{id}/{os}") // for IOS
//     public Clipboard clipboard(@PathVariable String id, @PathVariable String os) {
//         final Clipboard[] clipboard = { null }; // final for lambda

//         // get & remove if OK
//         clips.compute(id, (k, v) -> {
//             if (v != null && !v.getOs().equals(os)) { //不能消费自己推送的数据
//                 clipboard[0] = v; // save to return
//                 return null; // null to remove k-v mapping
//             }
//             return v;
//         });

//         Clipboard res = clipboard[0];
//         // 如果是过期的数据，返回空
//         if (res != null && Duration.between(res.getTime(), Instant.now()).toMinutes() >= EXPIRE_MINUTES) {
//             log.info("#Expired: {} - {}", Utils.omitSHA256(id), res);
//             res = null;
//         }
//         return res == null ? new Clipboard() : res;
//     }

//     @PostMapping("/clipboard/{id}/{os}")
//     @ResponseStatus(HttpStatus.OK)
//     public void copyToCloud(@PathVariable String id, @PathVariable String os, @RequestBody Clipboard clipboard) {
//         clipboard.setOs(os);
//         //目前仅支持单Windows + 单IOS，后续可以加入设备ID区分不同Windows

//         AtomicBoolean isPushed = new AtomicBoolean(false);
//         waitlist.computeIfPresent(id, (k, v) -> {
//             if (os.equals("ios")) { //仅IOS向Windows推送， IOS仅快捷指令手动Get
//                 v.setResult(clipboard); //通知长轮询
//                 isPushed.set(true);
//                 log.info("Pushed to {}", Utils.omitSHA256(id)); // 不能输出完整的id，防止泄露数据
//             }
//             return v;
//         });

//         if (!isPushed.get())
//             this.clips.put(id, clipboard); //仅当没有推送时，才保存到clipboards

//         log.info("{}: {}", Utils.omitSHA256(id), clipboard);
//     }

//     @GetMapping("/clipboard/status")
//     public Object clipboardStatus() {
//         Map<String, Object> res = new HashMap<>();
//         res.put("size", clips.size());
//         res.put("polling", waitlist.size()); //users
//         return res;
//     }
// }

package com.mrbeanc.controller;

import com.mrbeanc.model.Clipboard;
import com.mrbeanc.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import jakarta.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class ClipboardController {

    private static final Logger log = LoggerFactory.getLogger(ClipboardController.class);

    @Value("${clipboard.expire.minutes}")
    private int EXPIRE_MINUTES;

    @Resource(name = "clipboards")
    private Map<String, Clipboard> clips;

    private final Map<String, DeferredResult<Clipboard>> waitlist = new ConcurrentHashMap<>();

    /**
     * 长轮询接口，用于获取剪贴板数据
     * @param id 唯一标识符
     * @param os 操作系统标识
     * @return DeferredResult 包含剪贴板数据
     */
    @GetMapping("/clipboard/long-polling/{id}/{os}")
    public DeferredResult<Clipboard> clipboardLongPolling(@PathVariable String id, @PathVariable String os) {
        DeferredResult<Clipboard> deferredResult = new DeferredResult<>(60_000L); // 超时时间为 60 秒

        // 清理超时或完成的请求
        deferredResult.onCompletion(() -> waitlist.remove(id, deferredResult));

        AtomicBoolean hasData = new AtomicBoolean(false);
        clips.computeIfPresent(id, (k, v) -> {
            if (!v.getOs().equals(os)) { // 如果不是自己推送的数据，直接返回
                deferredResult.setResult(v); // 返回数据
                hasData.set(true);
                return null; // 移除已消费的数据
            }
            return v; // 保留未消费的数据
        });

        if (!hasData.get()) {
            waitlist.put(id, deferredResult); // 没有数据时，将请求加入等待队列
        }

        return deferredResult;
    }

    /**
     * 普通 GET 接口，用于获取剪贴板数据（适用于 iOS）
     * @param id 唯一标识符
     * @param os 操作系统标识
     * @return 剪贴板数据
     */
    @GetMapping("/clipboard/{id}/{os}")
    public Clipboard clipboard(@PathVariable String id, @PathVariable String os) {
        Clipboard clipboard = clips.get(id);

        if (clipboard != null && !clipboard.getOs().equals(os)) { // 不能消费自己推送的数据
            if (Duration.between(clipboard.getTime(), Instant.now()).toMinutes() >= EXPIRE_MINUTES) {
                log.info("Expired: {} - {}", Utils.omitSHA256(id), clipboard);
                return new Clipboard(); // 返回空的剪贴板对象
            }
            return clipboard;
        }
        return new Clipboard();
    }

    /**
     * 上传剪贴板数据到云端
     * @param id 唯一标识符
     * @param os 操作系统标识
     * @param clipboard 剪贴板数据
     */
    @PostMapping("/clipboard/{id}/{os}")
    @ResponseStatus(HttpStatus.OK)
    public void copyToCloud(@PathVariable String id, @PathVariable String os, @RequestBody Clipboard clipboard) {
        clipboard.setOs(os);

        AtomicBoolean isPushed = new AtomicBoolean(false);
        waitlist.computeIfPresent(id, (k, deferredResult) -> {
            if (os.equals("ios")) { // 仅 iOS 推送到 Windows
                deferredResult.setResult(clipboard);
                isPushed.set(true);
                log.info("Pushed to {}", Utils.omitSHA256(id));
            }
            return deferredResult;
        });

        if (!isPushed.get()) {
            clips.put(id, clipboard); // 如果没有推送，则保存数据
        }

        log.info("{}: {}", Utils.omitSHA256(id), clipboard);
    }

    /**
     * 获取剪贴板状态（例如当前保存的数据量）
     * @return 剪贴板状态
     */
    @GetMapping("/clipboard/status")
    public Object clipboardStatus() {
        Map<String, Object> res = new HashMap<>();
        res.put("size", clips.size());
        res.put("polling", waitlist.size());
        return res;
    }
}