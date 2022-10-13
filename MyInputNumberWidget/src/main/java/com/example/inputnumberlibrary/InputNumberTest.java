package com.example.inputnumberlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * CustomViewNumberCount
 *
 * @author Harmsworth
 * @date 2022~10~13~14:41
 */
public class InputNumberTest extends RelativeLayout {
    private static final String TAG = "custom";
    private int mCurrentNumber;
    private View mMinusBtn;
    private View mPlusBtn;
    private EditText mEditTxv;
    private onNumberChangedListener onNumberCHangedListener;
    private int mMin;
    private int mMax;
    private int mStep;
    private int mDefaultValue;
    private boolean mEnable;
    private int mBtnBgRes;

    public InputNumberTest(Context context) {
        this(context, null);
    }

    public InputNumberTest(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputNumberTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //初始化参数
        initAttrs(context, attrs);
        //设置组件
        initView(context);
        //设置事件
        setUpEvent();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InputNumber);
        mMin = a.getInt(R.styleable.InputNumber_min, 0);
        mMax = a.getInt(R.styleable.InputNumber_max, 0);
        mStep = a.getInt(R.styleable.InputNumber_step, 1);

        mDefaultValue = a.getInt(R.styleable.InputNumber_defaultValue, 0);
        this.mCurrentNumber = mDefaultValue;
        updateText();

        mEnable = a.getBoolean(R.styleable.InputNumber_enable, true);
        mMinusBtn.setEnabled(mEnable);
        mPlusBtn.setEnabled(mEnable);
        mEditTxv.setEnabled(mEnable);

        mBtnBgRes = a.getResourceId(R.styleable.InputNumber_btnBackgroundColor, -1);
    }

    /**
     * 初始化View
     *
     * @param context 自定义组件上下文
     */
    private void initView(Context context) {
        //以下代码等价
        //attachToRoot: true/不填 自动装填， false 用户自己装填
        //LayoutInflater.from(context).inflate(R.layout.layout_input_view, this, true);
        //LayoutInflater.from(context).inflate(R.layout.layout_input_view, this);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_input_number_view, this, false);
        addView(view);

        mMinusBtn = findViewById(R.id.minus_btn);
        mPlusBtn = findViewById(R.id.plus_btn);
        mEditTxv = findViewById(R.id.edit_txv);
    }

    /**
     * 设置事件
     */
    private void setUpEvent() {
        mMinusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentNumber -= mStep;
                updateText();
            }
        });

        mPlusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentNumber += mStep;
                updateText();
            }
        });
    }

    /**
     * 更新数据
     */
    private void updateText() {
        if (mCurrentNumber >= mMax) {
            mCurrentNumber = mMax;
            mPlusBtn.setEnabled(false);
            Log.d(TAG, "current number is max value...");
        } else if (mCurrentNumber <= mMin) {
            mCurrentNumber = mMin;
            mMinusBtn.setEnabled(false);
            Log.d(TAG, "current number is min value...");
        }else {
            mPlusBtn.setEnabled(true);
            mMinusBtn.setEnabled(true);
        }

        mEditTxv.setText(String.valueOf(mCurrentNumber));

        if (onNumberCHangedListener != null) {
            onNumberCHangedListener.onNumberChanged(mCurrentNumber);
        }
    }

    public void setOnNumberChangedListener(onNumberChangedListener listener) {
        this.onNumberCHangedListener = listener;
    }

    public interface onNumberChangedListener {
        void onNumberChanged(int value);
    }

    public int getNumber() {
        return mCurrentNumber;
    }

    public void setNumber(int mCurrentNumber) {
        this.mCurrentNumber = mCurrentNumber;
        updateText();
    }

    public int getMin() {
        return mMin;
    }

    public void setMin(int mMin) {
        this.mMin = mMin;
    }

    public int getMax() {
        return mMax;
    }

    public void setMax(int mMax) {
        this.mMax = mMax;
    }

    public int getStep() {
        return mStep;
    }

    public void setStep(int mStep) {
        this.mStep = mStep;
    }

    public boolean isDisable() {
        return mEnable;
    }

    public void setDisable(boolean mDisable) {
        this.mEnable = mDisable;
    }

    public int getBtnBgRes() {
        return mBtnBgRes;
    }

    public void setBtnBgRes(int mBtnBgRes) {
        this.mBtnBgRes = mBtnBgRes;
    }

    public int getDefaultValue() {
        return mDefaultValue;
    }

    public void setDefaultValue(int mDefaultValue) {
        this.mDefaultValue = mDefaultValue;
        this.mCurrentNumber = mDefaultValue;
        this.updateText();
    }
}
