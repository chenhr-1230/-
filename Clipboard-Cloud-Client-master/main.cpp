#include "widget.h"
#include <QApplication>
#include <QNetworkProxy>

int main(int argc, char *argv[])
{
    QApplication::setHighDpiScaleFactorRoundingPolicy(Qt::HighDpiScaleFactorRoundingPolicy::PassThrough);

    QApplication a(argc, argv);

    QApplication::setQuitOnLastWindowClosed(false); //否则，在窗口没有show的时候，弹窗，并关闭弹窗，程序居然会退出
    QNetworkProxy::setApplicationProxy(QNetworkProxy::NoProxy); //禁用全局代理，为什么Qt默认走代理啊喂！
    QFont font("PingFang SC", 12); // macOS 默认字体
    a.setFont(font);
    Widget w;
    w.show();
    return a.exec();
}
