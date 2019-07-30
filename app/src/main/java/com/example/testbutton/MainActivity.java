package com.example.testbutton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.testbutton.Fragment.FixedSpeedScroll;
import com.example.testbutton.Fragment.MyFragmentPaperAdapter;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {
    private Button btn_up;
    private Button btn_down;
    private Button btn_left;
    private Button btn_right;

    private RecyclerView mRecyclerViewHead;
    private RecyclerView mRecyclerViewLeft;
    private RecyclerView mRecyclerViewBody;

    private ViewPager mViewPagerHead;
    private RadioGroup mTabRadioGroup;

    private MyAdapterHead adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Observable mObservable;
    private Observable mKeyObservable;
    private OperationControl mOperationControl;

    //用一个Map来存所有RecyclerView? Key:H X L X B X, value: recyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MyButton myButton = findViewById(R.id.my_btn);
        initControlButtom();
        initHeaderButton();


        //initLeftButton();
        //initBodyButton();
        //initHeadFragment();


//        setDispatchListener(new DispatchListener() {
//            @Override
//            public void setOperationControl(final KeyEvent event) {
//                mKeyObservable = Observable.create(new ObservableOnSubscribe<String>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                        Log.d("Key", "KeyAction; " + event.getAction());
//                        Log.d("Key", "KeyCode; " + event.getKeyCode());
//                        switch (event.getAction()) {
//                            case KeyEvent.ACTION_DOWN:
//                                //按下，两种情况
//                                //1.长按。如果长按，接下来的key events仍为ACTION_DOWN,以及一个非0的值用以getRepeatCount().
//                                if (event.getRepeatCount() > 6) {
//                                    if (event.getRepeatCount() % 3 == 0) {
//                                        //每3 次发送方向
//                                        switch (event.getKeyCode()) {
//                                            case KeyEvent.KEYCODE_DPAD_UP:
//                                                Toast.makeText(MainActivity.this,"up",Toast.LENGTH_LONG);
//                                                emitter.onNext("up");
//                                                break;
//                                            case KeyEvent.KEYCODE_DPAD_DOWN:
//                                                Toast.makeText(MainActivity.this,"down",Toast.LENGTH_LONG);
//                                                emitter.onNext("down");
//                                                break;
//                                            case KeyEvent.KEYCODE_DPAD_LEFT:
//                                                Toast.makeText(MainActivity.this,"left",Toast.LENGTH_LONG);
//                                                emitter.onNext("left");
//                                                break;
//                                            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                                                Toast.makeText(MainActivity.this,"right",Toast.LENGTH_LONG);
//                                                emitter.onNext("right");
//                                                break;
//                                            default:
//                                        }
//                                    }
//                                }
//
//
//                                break;
//                            case KeyEvent.ACTION_UP:
//                                //松开
//
//                                //短按,重复小于5，且操作未被取消
//                                if (event.getRepeatCount() < 6) {
//                                    if (event.getRepeatCount() % 5 == 0 && event.getFlags() != KeyEvent.FLAG_CANCELED) {
//                                        //只发送一次
//                                        switch (event.getKeyCode()) {
//                                            case KeyEvent.KEYCODE_DPAD_UP:
//                                                Toast.makeText(MainActivity.this,"up",Toast.LENGTH_LONG);
//                                                emitter.onNext("up");
//                                                break;
//                                            case KeyEvent.KEYCODE_DPAD_DOWN:
//                                                emitter.onNext("down");
//                                                Toast.makeText(MainActivity.this,"down",Toast.LENGTH_LONG);
//                                                break;
//                                            case KeyEvent.KEYCODE_DPAD_LEFT:
//                                                Toast.makeText(MainActivity.this,"left",Toast.LENGTH_LONG);
//                                                emitter.onNext("left");
//                                                break;
//                                            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                                                Toast.makeText(MainActivity.this,"right",Toast.LENGTH_LONG);
//                                                emitter.onNext("right");
//                                                break;
//                                            default:
//                                        }
//                                    }
//                                }
//                                break;
//                        }
//                    }
//                });
//            }
//
////            @Override
////            public void setOperationControl() {
//////                if(hasFired = false){
//////                    mOperationControl = new OperationControl(mKeyObservable);
//////                    mOperationControl.setRecyclerViewHead(mRecyclerViewHead);
//////                    mOperationControl.Control();
//////                    hasFired = true;
//////                }
////
////                mOperationControl = new OperationControl(mKeyObservable);
////                mOperationControl.setRecyclerViewHead(mRecyclerViewHead);
////                mOperationControl.Control();
////            }
//        });

        //initKeyControl();
        //mOperationControl = new OperationControl(mKeyObservable);
        mOperationControl = new OperationControl(mObservable);
        mOperationControl.setRecyclerViewHead(mRecyclerViewHead);
        mOperationControl.Control();
    }

//    private void initHeadFragment() {
//        mViewPagerHead = findViewById(R.id.viewpager);
//        mTabRadioGroup = findViewById(R.id.radiogroup);
//
//        mViewPagerHead.setAdapter(new MyFragmentPaperAdapter(getSupportFragmentManager()));
//        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                for (int i = 0; i < radioGroup.getChildCount(); i++) {
//                    if (radioGroup.getChildAt(i).getId() == checkedId) {
//                        mViewPagerHead.setCurrentItem(i);
//                        return;
//                    }
//                }
//            }
//        });
//    }

    private void initHeaderButton() {
        mRecyclerViewHead = findViewById(R.id.rv_head);
        mViewPagerHead = findViewById(R.id.viewpager);

        mViewPagerHead.setAdapter(new MyFragmentPaperAdapter(getSupportFragmentManager(), 10));
        mViewPagerHead.setPageTransformer(true, new ViewPager.PageTransformer() {
            private static final float MIN_SCALE = 0.75f;

            /*
            position的可能性的值有：
            [-Infinity,-1)  已经看不到了
            (1,+Infinity] 已经看不到了
            [-1,1]

            假设现在ViewPager在A页现在滑出B页，则:
            A页的position变化就是( -1, 0]
            B页的position变化就是[ 0 , 1 ]
             */
            @Override
            public void transformPage(@NonNull View page, float position) {
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float rotate = 10 * Math.abs(position);
                //position小于等于1的时候，代表page已经位于中心item的最左边，
                //此时设置为最小的缩放率以及最大的旋转度数
                if (position <= -1) {
                    page.setScaleX(MIN_SCALE);
                    page.setScaleY(MIN_SCALE);
                    page.setRotationY(rotate);
                }

                if (position > -1 && position < 0) {//A页的动画
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                    page.setRotationY(rotate);
                }

                if (position >= 0 && position < 1) {//B页的动画
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                    page.setRotationY(-rotate);
                }

                if (position >= 1) {//position大于等于1的时候，代表page已经位于中心item的最右边
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                    page.setRotationY(-rotate);
                }
            }
        });
        mViewPagerHead.setFocusable(false);

        //通过反射修改切换速度
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            FixedSpeedScroll mScroller = new FixedSpeedScroll(mViewPagerHead.getContext(), new AccelerateInterpolator());
            mField.set(mViewPagerHead, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //adapter = new MyAdapterHead(MainActivity.this, mObservable, mRecyclerViewHead);
        mLayoutManager = new MyLinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);


        mRecyclerViewHead.setAdapter(new MyAdapterHead(MainActivity.this, mObservable, mRecyclerViewHead, mViewPagerHead));
        mRecyclerViewHead.setLayoutManager(mLayoutManager);

    }

//    private void initLeftButton() {
//        mRecyclerViewLeft = findViewById(R.id.rv_left);
//
//        adapter = new MyAdapterHead(MainActivity.this, mObservable, mRecyclerViewHead);
//        mLayoutManager = new MyLinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
//        //((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerViewLeft.setAdapter(adapter);
//        mRecyclerViewLeft.setLayoutManager(mLayoutManager);
//    }
//
//    private void initBodyButton() {
//        mRecyclerViewBody = findViewById(R.id.rv_body);
//
//        //adapter = new MyAdapterHead(MainActivity.this,mObservable, mRecyclerViewHead);
//
//        //mLayoutManager = new MyLinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
//        //((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerViewBody.setAdapter(new MyAdapterBody(MainActivity.this));
//        mRecyclerViewBody.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
//    }

    private void initControlButtom() {
        btn_up = findViewById(R.id.btn_up);
        btn_down = findViewById(R.id.btn_down);
        btn_left = findViewById(R.id.btn_left);
        btn_right = findViewById(R.id.btn_right);

        //        btn_up.

        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                btn_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emitter.onNext("up");
                    }
                });
                btn_down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emitter.onNext("down");
                    }
                });
                btn_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emitter.onNext("right");
                    }
                });
                btn_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emitter.onNext("left");
                    }
                });
            }
        })
                //.debounce(100, TimeUnit.MILLISECONDS)
                ;

        mObservable = observable;
    }

    private DispatchListener mDispatchListener;
    private boolean hasFired = false;

    public interface DispatchListener {
        void setOperationControl(KeyEvent event);
    }

    public void setDispatchListener(DispatchListener dispatchListener) {
        mDispatchListener = dispatchListener;
    }


    @Override
    public boolean dispatchKeyEvent(final KeyEvent event) {


        if(mDispatchListener!=null){
            mDispatchListener.setOperationControl(event);
        }

//        mOperationControl = new OperationControl(mKeyObservable);
//        mOperationControl.setRecyclerViewHead(mRecyclerViewHead);
//        mOperationControl.Control();
        //继续分发事件，反正也没写
        //return super.dispatchKeyEvent(event);
        //拦截原生
        return true;
    }

    public void initKeyControl(){
        mKeyObservable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(final ObservableEmitter emitter) throws Exception {
                setDispatchListener(new DispatchListener() {
                    @Override
                    public void setOperationControl(KeyEvent event) {
                        switch (event.getAction()) {
                            case KeyEvent.ACTION_DOWN:
                                //按下，两种情况
                                //1.长按。如果长按，接下来的key events仍为ACTION_DOWN,以及一个非0的值用以getRepeatCount().
                                if (event.getRepeatCount() > 6) {
                                    if (event.getRepeatCount() % 3 == 0) {
                                        //每3 次发送方向
                                        switch (event.getKeyCode()) {
                                            case KeyEvent.KEYCODE_DPAD_UP:
                                                Toast.makeText(MainActivity.this,"up",Toast.LENGTH_LONG);
                                                emitter.onNext("up");
                                                break;
                                            case KeyEvent.KEYCODE_DPAD_DOWN:
                                                Toast.makeText(MainActivity.this,"down",Toast.LENGTH_LONG);
                                                emitter.onNext("down");
                                                break;
                                            case KeyEvent.KEYCODE_DPAD_LEFT:
                                                Toast.makeText(MainActivity.this,"left",Toast.LENGTH_LONG);
                                                emitter.onNext("left");
                                                break;
                                            case KeyEvent.KEYCODE_DPAD_RIGHT:
                                                Toast.makeText(MainActivity.this,"right",Toast.LENGTH_LONG);
                                                emitter.onNext("right");
                                                break;
                                            default:
                                        }
                                    }
                                }


                                break;
                            case KeyEvent.ACTION_UP:
                                //松开

                                //短按,重复小于5，且操作未被取消
                                if (event.getRepeatCount() < 6) {
                                    if (event.getRepeatCount() % 5 == 0 && event.getFlags() != KeyEvent.FLAG_CANCELED) {
                                        //只发送一次
                                        switch (event.getKeyCode()) {
                                            case KeyEvent.KEYCODE_DPAD_UP:
                                                Toast.makeText(MainActivity.this,"up",Toast.LENGTH_LONG);
                                                emitter.onNext("up");
                                                break;
                                            case KeyEvent.KEYCODE_DPAD_DOWN:
                                                emitter.onNext("down");
                                                Toast.makeText(MainActivity.this,"down",Toast.LENGTH_LONG);
                                                break;
                                            case KeyEvent.KEYCODE_DPAD_LEFT:
                                                Toast.makeText(MainActivity.this,"left",Toast.LENGTH_LONG);
                                                emitter.onNext("left");
                                                break;
                                            case KeyEvent.KEYCODE_DPAD_RIGHT:
                                                Toast.makeText(MainActivity.this,"right",Toast.LENGTH_LONG);
                                                emitter.onNext("right");
                                                break;
                                            default:
                                        }
                                    }
                                }
                                break;
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }


    @SuppressLint("RestrictedApi")
    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return super.dispatchKeyShortcutEvent(event);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean superDispatchKeyEvent(KeyEvent event) {
        return super.superDispatchKeyEvent(event);
    }

//    当键盘按下时
//            首先触发dispatchKeyEvent
//    然后触发onUserInteraction
//            再次onKeyDown
//    如果按下紧接着松开，则是俩步
//            紧跟着触发dispatchKeyEvent
//    然后触发onUserInteraction
//            再次onKeyUp
}

