/********************************************************************************
** Form generated from reading UI file 'tipwidget.ui'
**
** Created by: Qt User Interface Compiler version 6.8.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_TIPWIDGET_H
#define UI_TIPWIDGET_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_TipWidget
{
public:

    void setupUi(QWidget *TipWidget)
    {
        if (TipWidget->objectName().isEmpty())
            TipWidget->setObjectName("TipWidget");
        TipWidget->resize(87, 75);

        retranslateUi(TipWidget);

        QMetaObject::connectSlotsByName(TipWidget);
    } // setupUi

    void retranslateUi(QWidget *TipWidget)
    {
        TipWidget->setWindowTitle(QCoreApplication::translate("TipWidget", "Form", nullptr));
    } // retranslateUi

};

namespace Ui {
    class TipWidget: public Ui_TipWidget {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_TIPWIDGET_H
