package github.tiger.fourierview;

import android.graphics.Point;

/**
 * Created by Administrator on 2016/3/15.
 */
public class Circle {
    //主颜色
    private int color;
    //半径
    private int radius;
    //圆心
    private Point mCenterPoint;
    //圆上动点当前位置
    private Point mMovePoint;
    //当前动点移动角度
    private int mCurrentDegree;
    //动点角度增量
    private int mIncreaseDegree;
    public Point getmMovePoint() {
        return mMovePoint;
    }

    public void setmMovePoint(Point mMovePoint) {
        this.mMovePoint = mMovePoint;
    }

    public Circle(int color){
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Point getmCenterPoint() {
        return mCenterPoint;
    }

    public void setmCenterPoint(Point mCenterPoint) {
        this.mCenterPoint = mCenterPoint;
    }

    public int getmCurrentDegree() {
        return mCurrentDegree;
    }

    public void setmCurrentDegree(int mCurrentDegree) {
        this.mCurrentDegree = mCurrentDegree;
    }

    public int getmIncreaseDegree() {
        return mIncreaseDegree;
    }

    public void setmIncreaseDegree(int mIncreaseDegree) {
        this.mIncreaseDegree = mIncreaseDegree;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "color=" + color +
                ", radius=" + radius +
                ", mCenterPoint=" + mCenterPoint +
                ", mCurrentDegree=" + mCurrentDegree +
                ", mIncreaseDegree=" + mIncreaseDegree +
                '}';
    }
}
