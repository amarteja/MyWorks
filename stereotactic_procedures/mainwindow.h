#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include<QtCore>
#include <QImage>
#include <QColor>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();
    void paintEvent(QPaintEvent *);
       void mousePressEvent(QMouseEvent *);
       void mouseMoveEvent(QMouseEvent *);


private slots:
    void on_commandLinkButton_clicked();
    void on_PushButton_clicked();
    void on_endEffector_clicked();
    void on_chooseRestrictedRegion_clicked();
    void checkRestrictedArea();
    void on_ConfirmRes_clicked();
    void on_SugeryCheck_clicked();

private:
    Ui::MainWindow *ui;
    QImage * image;
    bool setTarget;
    bool setEffector;
    double targetX;
    double targetY;
    double endEffectorX;
    double endEffectorY;
    double ex;
    double ey;
    bool showImage;
    bool restrictedRegion;
    int rrx[10000];
    int rry[10000];
    int rrcount;
};

#endif // MAINWINDOW_H
