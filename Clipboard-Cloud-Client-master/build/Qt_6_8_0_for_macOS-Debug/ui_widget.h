/********************************************************************************
** Form generated from reading UI file 'widget.ui'
**
** Created by: Qt User Interface Compiler version 6.8.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_WIDGET_H
#define UI_WIDGET_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QFormLayout>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QSpacerItem>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_Widget
{
public:
    QVBoxLayout *verticalLayout;
    QFormLayout *formLayout;
    QLabel *label_server;
    QLabel *label_userid;
    QLineEdit *edit_userid;
    QLabel *label_uuid;
    QHBoxLayout *horizontalLayout;
    QLineEdit *edit_uuid;
    QPushButton *btn_uuid_reset;
    QLabel *label;
    QLabel *label_qr;
    QHBoxLayout *horizontalLayout_4;
    QLineEdit *edit_server;
    QPushButton *btn_server_test;
    QHBoxLayout *horizontalLayout_2;
    QLabel *label_3;
    QSpacerItem *horizontalSpacer;
    QLabel *label_2;
    QPushButton *btn_save;

    void setupUi(QWidget *Widget)
    {
        if (Widget->objectName().isEmpty())
            Widget->setObjectName("Widget");
        Widget->resize(752, 417);
        Widget->setStyleSheet(QString::fromUtf8("font: 10pt \"Microsoft YaHei UI\";"));
        verticalLayout = new QVBoxLayout(Widget);
        verticalLayout->setObjectName("verticalLayout");
        formLayout = new QFormLayout();
        formLayout->setObjectName("formLayout");
        label_server = new QLabel(Widget);
        label_server->setObjectName("label_server");

        formLayout->setWidget(0, QFormLayout::LabelRole, label_server);

        label_userid = new QLabel(Widget);
        label_userid->setObjectName("label_userid");

        formLayout->setWidget(1, QFormLayout::LabelRole, label_userid);

        edit_userid = new QLineEdit(Widget);
        edit_userid->setObjectName("edit_userid");

        formLayout->setWidget(1, QFormLayout::FieldRole, edit_userid);

        label_uuid = new QLabel(Widget);
        label_uuid->setObjectName("label_uuid");

        formLayout->setWidget(2, QFormLayout::LabelRole, label_uuid);

        horizontalLayout = new QHBoxLayout();
        horizontalLayout->setObjectName("horizontalLayout");
        edit_uuid = new QLineEdit(Widget);
        edit_uuid->setObjectName("edit_uuid");
        edit_uuid->setReadOnly(true);

        horizontalLayout->addWidget(edit_uuid);

        btn_uuid_reset = new QPushButton(Widget);
        btn_uuid_reset->setObjectName("btn_uuid_reset");

        horizontalLayout->addWidget(btn_uuid_reset);


        formLayout->setLayout(2, QFormLayout::FieldRole, horizontalLayout);

        label = new QLabel(Widget);
        label->setObjectName("label");

        formLayout->setWidget(3, QFormLayout::FieldRole, label);

        label_qr = new QLabel(Widget);
        label_qr->setObjectName("label_qr");
        label_qr->setAlignment(Qt::AlignCenter);

        formLayout->setWidget(4, QFormLayout::FieldRole, label_qr);

        horizontalLayout_4 = new QHBoxLayout();
        horizontalLayout_4->setObjectName("horizontalLayout_4");
        edit_server = new QLineEdit(Widget);
        edit_server->setObjectName("edit_server");

        horizontalLayout_4->addWidget(edit_server);

        btn_server_test = new QPushButton(Widget);
        btn_server_test->setObjectName("btn_server_test");

        horizontalLayout_4->addWidget(btn_server_test);


        formLayout->setLayout(0, QFormLayout::FieldRole, horizontalLayout_4);


        verticalLayout->addLayout(formLayout);

        horizontalLayout_2 = new QHBoxLayout();
        horizontalLayout_2->setObjectName("horizontalLayout_2");
        horizontalLayout_2->setSizeConstraint(QLayout::SetDefaultConstraint);
        label_3 = new QLabel(Widget);
        label_3->setObjectName("label_3");
        label_3->setOpenExternalLinks(true);

        horizontalLayout_2->addWidget(label_3);

        horizontalSpacer = new QSpacerItem(40, 20, QSizePolicy::Policy::Expanding, QSizePolicy::Policy::Minimum);

        horizontalLayout_2->addItem(horizontalSpacer);

        label_2 = new QLabel(Widget);
        label_2->setObjectName("label_2");
        label_2->setOpenExternalLinks(true);

        horizontalLayout_2->addWidget(label_2);

        btn_save = new QPushButton(Widget);
        btn_save->setObjectName("btn_save");

        horizontalLayout_2->addWidget(btn_save);


        verticalLayout->addLayout(horizontalLayout_2);


        retranslateUi(Widget);

        QMetaObject::connectSlotsByName(Widget);
    } // setupUi

    void retranslateUi(QWidget *Widget)
    {
        Widget->setWindowTitle(QCoreApplication::translate("Widget", "Widget", nullptr));
        label_server->setText(QCoreApplication::translate("Widget", "Server", nullptr));
        label_userid->setText(QCoreApplication::translate("Widget", "User ID", nullptr));
#if QT_CONFIG(tooltip)
        edit_userid->setToolTip(QString());
#endif // QT_CONFIG(tooltip)
        edit_userid->setPlaceholderText(QCoreApplication::translate("Widget", "Just for safe. Can be Empty.", nullptr));
        label_uuid->setText(QCoreApplication::translate("Widget", "UUID", nullptr));
        edit_uuid->setPlaceholderText(QCoreApplication::translate("Widget", "UUID", nullptr));
        btn_uuid_reset->setText(QCoreApplication::translate("Widget", "reset", nullptr));
        label->setText(QCoreApplication::translate("Widget", "<html><head/><body><p><span style=\" font-style:italic;\">Please copy UUID &amp; UserID to your IOS shortcuts.</span></p></body></html>", nullptr));
        label_qr->setText(QCoreApplication::translate("Widget", "QRCode", nullptr));
        edit_server->setPlaceholderText(QCoreApplication::translate("Widget", "Server url", nullptr));
        btn_server_test->setText(QCoreApplication::translate("Widget", "test", nullptr));
        label_3->setText(QCoreApplication::translate("Widget", "<html><head/><body><p><a href=\"https://blog.cls.ink/about/\"><span style=\" text-decoration: underline; color:#007af4;\">Maybe some Donate?\360\237\245\272</span></a></p></body></html>", nullptr));
        label_2->setText(QCoreApplication::translate("Widget", "<html><head/><body><p>You can get IOS shortcuts from <a href=\"https://github.com/MrBeanCpp/Clipboard-Cloud-Client\"><span style=\" text-decoration: underline; color:#007af4;\">Clipboard-Cloud-Client</span></a></p></body></html>", nullptr));
        btn_save->setText(QCoreApplication::translate("Widget", "Save\360\237\222\276", nullptr));
    } // retranslateUi

};

namespace Ui {
    class Widget: public Ui_Widget {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_WIDGET_H
