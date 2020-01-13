package com.golang.management.wigdet.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.golang.management.R;
import com.tencent.qcloud.tim.uikit.component.CircleImageView;

public class CircleList extends ViewGroup implements View.OnClickListener{
    //是否展开
    private boolean Isshow = false;
    //button数量
    private int mNum;
    //button对应的背景
    private int[] mButtonImage;
    //button数组
    protected CircleImageView[] mButtons;
    //画笔
    private Paint mPaint;
    //控件宽,高度
    private int mWidth;
    private int mHeight;
    //中间圆的半径
    private int mRadio;
    //四周圆的半径
    private int mRadio_;
    //设置为wrap_content时的大小
    private int mSize = 400;
    //按钮实时的位置
    private Point[] mPoints;
    //按钮收缩时的位置
    private Point[] mHidePoints;
    //按钮展开时位置
    private Point[] mShowPoints;
    private int MinSize;
    //中间按钮边距
    private int pading = 40;
    //用于按钮点击的对象
    private onItemClickListener onItemClickListener =null;
    //设置属性动画
    ValueAnimator mShowObjectAnimator;
    ValueAnimator mHideObjectAnimator;
    //接口
    public static interface onItemClickListener{
        void onClick(View v, int position);
    }
    //构造函数初始化对象
    public CircleList(Context context) {
        super(context);
        init(context,null);
    }

    public CircleList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CircleList(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public CircleList(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }


    //布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //循环为每一个按钮布局，根据点的位置和按钮的半径，调用子view自身的layout进行布局
        for(int i=0;i<mNum;i++){
            if(i==0){
                //中间的按钮
                mButtons[i].layout(mPoints[i].x-mRadio,mPoints[i].y-mRadio,mRadio*2+mPoints[i].x-mRadio,mRadio*2+mPoints[i].y-mRadio);
            }else {
                //四周的按钮
                mButtons[i].layout(mPoints[i].x-mRadio_,mPoints[i].y-mRadio_,mRadio_*2+mPoints[i].x-mRadio_,mRadio_*2+mPoints[i].y-mRadio_);
            }
        }
    }
    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //从参数中得到高度和宽度的测量规格
        //获取宽度和高度的测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取宽度和高度的大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = widthSize;
        int height = heightSize;
        switch (widthMode)
        {
            case MeasureSpec.UNSPECIFIED:
                width = widthSize;
                break;
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                //设置一个合适的值作为默认大小
                width = mSize;
                break;
        }
        switch (heightMode)
        {
            case MeasureSpec.UNSPECIFIED:
                height = heightSize;
                break;
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = mSize;
                break;
        }
        mWidth=width;
        mHeight=height;
        //设置测量后该控件的大小
        setMeasuredDimension(width,height);
        //因为我们需要一个正方形的布局，所以对高度和宽度进行判断，选择小的作为布局的依据
        MinSize = width > height ? height : width;
        this.pading = MinSize/10;
        //测量子view
        measureChildren(width,height);
    }

    @Override
    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        for(int i=(mNum-1);i>=0;i--) {
            int widthMS;
            int heightMS;
            double scale = Math.PI / (mNum - 2)/2;
            double radio = MinSize / 3;
            mHidePoints[i]=new Point(MinSize/4,MinSize/4);
            if (i == 0) {
                mRadio = MinSize / 4 - pading;
                mShowPoints[i] = new Point(MinSize / 4, MinSize / 4);
                widthMS = MeasureSpec.makeMeasureSpec(mRadio*2, MeasureSpec.EXACTLY);
                heightMS = MeasureSpec.makeMeasureSpec(mRadio*2, MeasureSpec.EXACTLY);
            } else {
                mRadio_ = mRadio / 3*2;
                mShowPoints[i] = new Point((int) (Math.cos((i - 1) * scale) * (radio+MinSize/4)+mRadio_), (int) (Math.sin((i - 1) * scale) * (radio+MinSize/4)+mRadio_));
                widthMS = MeasureSpec.makeMeasureSpec(mRadio_ * 2, MeasureSpec.EXACTLY);
                heightMS = MeasureSpec.makeMeasureSpec(mRadio_ * 2, MeasureSpec.EXACTLY);
            }
            mPoints = Isshow ? mShowPoints : mHidePoints;
            measureChild(mButtons[i],widthMS,heightMS);
        }
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        child.measure(parentWidthMeasureSpec,parentHeightMeasureSpec);
    }

    private void init(Context context,AttributeSet attr)
    {
        TypedArray typedArray = context.obtainStyledAttributes(attr, R.styleable.CircleList);
        this.mNum = typedArray.getInt(R.styleable.CircleList_button_num,5);
        this.Isshow = typedArray.getBoolean(R.styleable.CircleList_is_show,false);
        this.mButtons = new CircleImageView[mNum];
        this.mPoints = new Point[mNum];
        this.mHidePoints = new Point[mNum];
        this.mShowPoints = new Point[mNum];
        int[] buttonImage=new int[]{R.mipmap.release_circle,R.mipmap.release_move};
        this.mButtonImage = buttonImage;
        for(int i=(mNum-1);i>=0;i--)
        {
            mButtons[i] = new CircleImageView(context,attr);
            //mButtons[i].setBackgroundResource(mButtonImage[i]);
            mButtons[i].setImageBitmap(BitmapFactory.decodeResource(context.getResources(),mButtonImage[i]));
            mButtons[i].setOnClickListener(this);
            mButtons[i].setId(i);
            addView(mButtons[i],mNum-i-1);
        }
    }
    public void setmButtonImage(int[] mButtonImage) {
        this.mButtonImage = mButtonImage;
        for(int i=0;i<mNum;i++){
            mButtons[i].setImageBitmap(BitmapFactory.decodeResource(getResources(),mButtonImage[i]));
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==0){
            if(Isshow){
                HideObjectAnimator();
                Isshow=false;
            }else {
                ShowObjectAnimator();
                Isshow=true;
            }
            return;
        }
        if(onItemClickListener!=null){
            onItemClickListener.onClick(v,v.getId());
        }
    }

    public void setOnItemClickListener(onItemClickListener monItemClickListener){
        onItemClickListener = monItemClickListener;
    }

    public void ShowObjectAnimator(){
        if(mShowObjectAnimator==null){
            PointsChange hidePointChange = new PointsChange(mHidePoints);
            PointsChange showPointChange = new PointsChange(mShowPoints);
            mShowObjectAnimator = ValueAnimator.ofObject(new PointsEvaluator(),hidePointChange,showPointChange);
        }
        mShowObjectAnimator.setDuration(500);
        mShowObjectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @SuppressLint("WrongCall")
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointsChange ingPC = (PointsChange) animation.getAnimatedValue();
                mPoints = ingPC.getmPoints_();
                //Log.e("point",ingPC.getPoint(2).x+":"+ingPC.getPoint(2).y);
                onLayout(true,0,0,0,0);
            }
        });
        SetAnimator(mShowObjectAnimator,false);
        //mShowObjectAnimator.start();
    }

    public void HideObjectAnimator(){
        if(mHideObjectAnimator==null){
            PointsChange hidePointChange = new PointsChange(mHidePoints);
            PointsChange showPointChange = new PointsChange(mShowPoints);
            mHideObjectAnimator = ValueAnimator.ofObject(new PointsEvaluator(),showPointChange,hidePointChange);
        }
        mHideObjectAnimator.setDuration(500);
        mHideObjectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @SuppressLint("WrongCall")
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointsChange ingPC = (PointsChange) animation.getAnimatedValue();
                mPoints = ingPC.getmPoints_();
                //Log.e("point",ingPC.getPoint(2).x+":"+ingPC.getPoint(2).y);
                onLayout(true,0,0,0,0);
            }
        });
        SetAnimator(mHideObjectAnimator,true);
        //mHideObjectAnimator.start();
    }

    public void SetAnimator(ValueAnimator va,boolean isShow){
        ObjectAnimator[] mOA=new ObjectAnimator[mNum];
        ObjectAnimator[] mOA1=new ObjectAnimator[mNum];
        AnimatorSet animSet = new AnimatorSet();
        AnimatorSet.Builder mm =animSet.play(va);
        for(int i=1;i<mNum;i++){
            mOA[i]= ObjectAnimator.ofFloat(mButtons[i], "rotation", 0f, 10800f).setDuration(300);
            mOA1[i]=ObjectAnimator.ofFloat(mButtons[i], "rotation", 0f, 180000f).setDuration(600);
            if(isShow){
                mm=mm.with(mOA1[i]).after(mOA[i]);
            }else {
                mm=mm.with(mOA1[i]).before(mOA[i]);
            }
        }
        //animSet.setDuration(700);
        animSet.start();
    }


    public class PointsChange{
        private Point[] mPoints_;
        public PointsChange(Point[] points){
            mPoints_ = points;
        }
        public Point getPoint(int i){
            return mPoints_[i];
        }
        public Point[] getmPoints_(){
            return mPoints_;
        }
        public void setPoint(int i,Point point){
            mPoints_[i]=point;
        }

    }
    public class PointsEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            PointsChange startPC = (PointsChange)startValue;
            PointsChange endPC = (PointsChange)endValue;
            Point[] points =new Point[mNum];
            PointsChange ingPC =new PointsChange(points);
            Point point0 =new Point(endPC.getPoint(0).x,endPC.getPoint(0).y);
            ingPC.setPoint(0,point0);
            for(int i=1;i<mNum;i++){
                if(Isshow){
                    fraction = fraction+(i-1)*(i-1)*(i-1)*0.008f;
                }else {
                    fraction = fraction+((mNum-i-1)*(mNum-i-1)*(mNum-i-1)*0.008f);
                }
                if(fraction>1){
                    fraction =1.0f;
                }
                Point point =new Point((int)((endPC.getPoint(i).x-startPC.getPoint(i).x)*fraction+startPC.getPoint(i).x),(int)((endPC.getPoint(i).y-startPC.getPoint(i).y)*fraction+startPC.getPoint(i).y));
                //Log.e("point11",endPC.getPoint(i).x-startPC.getPoint(i).x+":"+point.x+":"+point.y);
                ingPC.setPoint(i,point);
            }
            return ingPC;
        }
    }
}