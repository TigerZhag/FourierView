package github.tiger.fourierview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/15.
 */
public class FourierView extends View {
    //第一个大圆的相关参数
    private int mMainColor = 0xFF009688;
    private int mCircleStartDegree = 0;
    private int mCircleIncreaseDegree = 4;
    private Point mCircleCenter = new Point(dp2px(50),dp2px(20));
    private int mLargeRadius = dp2px(30);
    private List<Circle> circles = new ArrayList<>();

    private Paint mPaint;

    //圆当前指向图像的点
    private Point mOriginWavePoint = new Point(dp2px(150),dp2px(100));
    //图像上的点的集合，用链表方便增删
    private int mWaveMaxLength = 180;
    private List<Point> mWavePoints = new LinkedList<>();

    public FourierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Circle circle = new Circle(mMainColor);
        circle.setmIncreaseDegree(mCircleIncreaseDegree);
        circle.setmCurrentDegree(mCircleStartDegree);
        circle.setRadius(mLargeRadius);
        circle.setmCenterPoint(mCircleCenter);

        circles.add(circle);
    }

    public void addCircle(Circle circle){
        int mMinRadius = 2;
        if (circles.size() > 0){
            mMinRadius = circles.get(circles.size() - 1).getRadius();
            if (mMinRadius <= 1) return;
        }
        circle.setRadius(circles.get(circles.size() - 1).getRadius()/3);
        circle.setmIncreaseDegree(circles.get(circles.size() - 1).getmIncreaseDegree() * 3);
        circles.add(circle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircle(canvas);

        drawWave(canvas);

        //重绘
        postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        },100);
    }

    private void drawWave(Canvas canvas) {
        //最外圈圆的动点坐标
        Point movePoint = circles.get(circles.size() - 1).getmMovePoint();
        //设置图像最左侧一点的纵坐标
        mOriginWavePoint.y = movePoint.y;
        //图像最长为180个像素
        if (mWavePoints.size() >= 180){
            mWavePoints.remove(0);
        }
        mWavePoints.add(new Point(mOriginWavePoint));
        //画一个点
        initPointPaint();
        canvas.drawPoint(mOriginWavePoint.x, mOriginWavePoint.y,mPaint);
        canvas.drawLine(movePoint.x,movePoint.y, mOriginWavePoint.x, mOriginWavePoint.y,mPaint);

        Path path = new Path();
        path.moveTo(mWavePoints.get(0).x,mWavePoints.get(0).y);
        for (int i = mWavePoints.size() - 1 ; i > 0; i --){
            path.lineTo(mWavePoints.get(i).x + mWavePoints.size() - i,mWavePoints.get(i).y);
        }
        canvas.drawPath(path,mPaint);
    }

    private void initPointPaint() {
        mPaint.setColor(0xFF2196F3);
        mPaint.setStrokeWidth(dp2px(4));
    }

    private void drawCircle(Canvas canvas) {
        canvas.save();
        initPaint();
        for (int i = 0; i < circles.size(); i++) {
            Circle circle = circles.get(i);
            //依次设置大圆动点为小圆圆心
            if (i == 0) {
                circle.setmCenterPoint(new Point(dp2px(50),dp2px(100)));
            }else {
                circle.setmCenterPoint(circles.get(i - 1).getmMovePoint());
            }
            initCirclePaint();
            //根据圆的颜色设置画笔
            mPaint.setColor(circle.getColor());
            canvas.drawCircle(circle.getmCenterPoint().x,circle.getmCenterPoint().y,
                    circle.getRadius(),mPaint);
            //根据动点旋转角度求坐标
            int movePointX = (int) (circle.getmCenterPoint().x + circle.getRadius() * Math.cos(Math.toRadians(circle.getmCurrentDegree())));
            int movePointY = (int) (circle.getmCenterPoint().y + circle.getRadius() * Math.sin(Math.toRadians(circle.getmCurrentDegree())));
            circle.setmMovePoint(new Point(movePointX,movePointY));
            //重设当前旋转角度
            int currentDegree = (circle.getmCurrentDegree() - circle.getmIncreaseDegree()) % 360;
            circle.setmCurrentDegree(currentDegree);
            //绘制圆心到动点连线
            canvas.drawLine(circle.getmCenterPoint().x,circle.getmCenterPoint().y,
                    movePointX,movePointY,mPaint);
        }
        canvas.restore();
    }

    private static final String TAG = "FourierView";
    private void initPaint() {
        if (mPaint == null){
            mPaint = new Paint();
        }else {
            mPaint.reset();
        }
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(dp2px(2));
    }

    private void initCirclePaint(){
        mPaint.setStyle(Paint.Style.STROKE);
    }


    private int dp2px(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,i, getResources().getDisplayMetrics());
    }


}
