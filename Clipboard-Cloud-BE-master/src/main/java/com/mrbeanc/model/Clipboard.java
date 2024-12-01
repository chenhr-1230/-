// package com.mrbeanc.model;

// import com.mrbeanc.util.Utils;
// import lombok.Data;

// import java.time.Instant;

// @Data
// public class Clipboard {
//     private String os = "";
//     private String data = "";
//     private Boolean isText = true;
//     private Instant time = Instant.now();

//     @Override
//     public String toString() {
//         int bytes = data.getBytes().length;
//         String displaySize;

//         // human-readable size
//         if (bytes < 1024) {
//             displaySize = String.format("%dB", bytes);
//         } else if (bytes < 1024 * 1024) {
//             displaySize = String.format("%.2fKB", bytes / 1024.0);
//         } else {
//             displaySize = String.format("%.2fMB", bytes / (1024.0 * 1024));
//         }

//         String _data = isText ?
//                 Utils.omit(data, 2) + String.format("[Text %s]", displaySize) :
//                 Utils.omit(data, 10) + String.format("[Image %s]", displaySize);

//         return "Clipboard{" +
//                 "os='" + os + '\'' +
//                 ", data='" + _data + '\'' +
//                 ", isText=" + isText +
//                 ", time=" + Utils.toLocalTime(time).toString().split("\\.")[0] +
//                 '}';
//     }
// }

package com.mrbeanc.model;

import com.mrbeanc.util.Utils;

import java.time.Instant;

public class Clipboard {
    private String os = "";
    private String data = "";
    private Boolean isText = true;
    private Instant time = Instant.now();

    // Constructor (optional, if needed)
    public Clipboard() {
    }

    // Getter and Setter for os
    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    // Getter and Setter for data
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    // Getter and Setter for isText
    public Boolean getIsText() {
        return isText;
    }

    public void setIsText(Boolean isText) {
        this.isText = isText;
    }

    // Getter and Setter for time
    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    @Override
    public String toString() {
        int bytes = data.getBytes().length;
        String displaySize;

        // human-readable size
        if (bytes < 1024) {
            displaySize = String.format("%dB", bytes);
        } else if (bytes < 1024 * 1024) {
            displaySize = String.format("%.2fKB", bytes / 1024.0);
        } else {
            displaySize = String.format("%.2fMB", bytes / (1024.0 * 1024));
        }

        String _data = isText ?
                Utils.omit(data, 2) + String.format("[Text %s]", displaySize) :
                Utils.omit(data, 10) + String.format("[Image %s]", displaySize);

        return "Clipboard{" +
                "os='" + os + '\'' +
                ", data='" + _data + '\'' +
                ", isText=" + isText +
                ", time=" + Utils.toLocalTime(time).toString().split("\\.")[0] +
                '}';
    }
}
