#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QPixmap>
#include <QFileDialog>
#include <QLabel>
#include <QMouseEvent>
#include <cstdlib>
#include <QLine>
#include <QColor>
#include <QPainter>
#include <QMessageBox>

using namespace std;


MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    image = new QImage("C:/sample1.png");
    setTarget = false;
    setEffector = false;
    targetX=targetY=endEffectorX=endEffectorY=0;
    restrictedRegion = false;
    rrcount = 0;
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_commandLinkButton_clicked()
{
    QString filename = QFileDialog::getOpenFileName(
                       this,
                       tr("Open File"),
                       "C://",
                       "Image files (*.dcm)"
                       );
    std::string filepath = filename.toUtf8().constData();
    std::string curDir = QApplication::applicationDirPath().toUtf8().constData();
    unsigned found = curDir.find_last_of("/\\");
    std::string curDir1 = curDir.substr(0,found);
    found = curDir1.find_last_of("/\\");
    std::string curDir2 = curDir.substr(0,found);
    std::string appPath = curDir2 + "\\Team3_stereotactic_procedures\\bin\\dcm.exe";
    QString qstr(appPath.c_str());
    //qDebug() << qstr;
    string tgt = appPath+" "+filepath;

    QStringList jpgfile = filename.split("//");
    int len = jpgfile.length();

    QStringList ql2 = jpgfile[len-1].split('.');
    ql2[1]="jpg";
    jpgfile[len-1]=ql2[0]+'.'+ql2[1];

    QString tmp = "";
    for(int i = 0; i < len ; i++)
    {
        tmp = tmp + jpgfile[i].toUtf8().constData();
        if(i != len-1)
        {tmp = tmp + "//";}
    }

    const char* command = tgt.c_str();

    QFile Fout(tmp);

    if(!Fout.exists())
    {}
    else
    {
     Fout.remove();
    }

    system(command);

    QImage newImage = QImage(tmp);
        delete image;
        image = new QImage(newImage);
        showImage=true;
        if(!Fout.exists())
        {}
        else
        {
         Fout.remove();
        }
}


void MainWindow::on_PushButton_clicked()
{
  setTarget = true;
  restrictedRegion = false;
}

void MainWindow::paintEvent(QPaintEvent *)
{
    QPainter painter(this);
    painter.drawImage(50,50,*image);
    painter.setPen(QPen(Qt::yellow,5));
    painter.drawPoint(targetX,targetY);
    painter.setPen(QPen(Qt::red, 2));

    if(targetX!=0 && targetY!=0 && endEffectorX!=0 && endEffectorY!=0)
    {
       ui->slope->setStyleSheet( "background-color: green" );
       painter.drawLine(targetX,targetY,endEffectorX,endEffectorY);
       checkRestrictedArea();
    }
    if(setEffector)
    {
       painter.setPen(QPen(Qt::cyan,5));
       double slope1 = abs((targetY - endEffectorY)/(targetX - endEffectorX));
       double slope2 = abs((targetY - ey)/(targetX - ex));
       if(abs(slope1 - slope2) < 0.03)
       {
            painter.drawPoint(ex,ey);
       }
    }
    for(int i=0;i<rrcount;i++)
    {
       painter.setPen(QPen(Qt::green,2));
       painter.drawPoint(rrx[i],rry[i]);
    }
}

void MainWindow::mousePressEvent(QMouseEvent *e)
{
    int x=e->x();
        int y=e->y();
        int image_startX=50;
        int image_startY=50;
        QSize qs = image->size();
        int image_endX=50+qs.width();
        int image_endY=50+qs.height();
        if(x> image_startX && x<image_endX && y>image_startY && y<image_endY)
        {
            if(setTarget)
            {
                setTarget=false;
                targetX=x;
                targetY=y;
                ui->start->setText("(" + QString::number(targetX)+
                                         ","+
                                         QString::number(targetY) + ")");
                repaint();
            }
            else
            {
            //setEffector = false;
            ex=x;
            ey=y;
            }
        }

}

void MainWindow::mouseMoveEvent(QMouseEvent *e)
{

    if(restrictedRegion)
    {

        rrx[rrcount]=e->x();
        rry[rrcount]=e->y();
        rrcount++;
    }
    else
    {
        if(!setEffector)
        {
        endEffectorX=e->x();
        endEffectorY=e->y();
        ui->label_2->setText("(" + QString::number(endEffectorX)+
                                 ","+
                                 QString::number(endEffectorY) + ")");
        }
        else
        {
            ex=e->x();
            ey=e->y();
            ui->label_3->setText("(" + QString::number(ex)+
                                     ","+
                                     QString::number(ey) + ")");
        }


    }
    repaint();
}

void MainWindow::on_endEffector_clicked()
{
    setEffector = true;

    ui->label_3->setText("(" + QString::number(ex)+
                             ","+
                             QString::number(ey) + ")");
    repaint();
}


void MainWindow::on_chooseRestrictedRegion_clicked()
{
  if(!restrictedRegion)
  {
      restrictedRegion = true;
  }

}

void MainWindow::checkRestrictedArea()
{
    for(int i=0; i<rrcount;i++)
    {
      double slope =(double)(endEffectorY - targetY) / (double)(endEffectorX-targetX);
      double rhs = slope*(rrx[i] - targetX);
      double lhs = rry[i]- targetY;
      if((rhs - lhs)>= -1 && (rhs - lhs)<=1 )
      {
          //if( endEffectorX < rrx[i] &&   rrx[i]< targetX && endEffectorY < rry[i] && rry[i]< targetY)
          //{
            ui->slope->setText("Restricted area");
            ui->slope->setStyleSheet( "background-color: red" );
            break;
          //}
          //if( targetX < rrx[i] && rrx[i]<endEffectorX && targetY < rry[i] && rry[i] < endEffectorY)
          //{
           //   ui->slope->setText("Restricted area");
            //  ui->slope->setStyleSheet( "background-color: red" );
             // break;
         // }

      }
      else
      {
          ui->slope->clear();
      }
    }

}


void MainWindow::on_ConfirmRes_clicked()
{
  restrictedRegion = false;
}


void MainWindow::on_SugeryCheck_clicked()
{
    int toolength = ui->lineEdit->text().toInt();
    toolength = toolength*10;
    int distance = sqrt(pow((double)(endEffectorX - targetX),2) + pow((double)(endEffectorY - targetY),2));
    QString s = QString::number(distance);
    qDebug() << s;
    if(toolength < distance)
    {
      QMessageBox msg;
      msg.critical(0,"Error","The surgery cannot be done with this length of tool");
      msg.setFixedSize(500,200);
    }
    else
    {
        QMessageBox msg(QMessageBox::Question,"Tool Length","The surgery can be done with this length of tool",QMessageBox::Ok);
        msg.exec();

    }

}
