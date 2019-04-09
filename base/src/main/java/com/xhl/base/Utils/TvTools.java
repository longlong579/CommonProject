package com.xhl.base.Utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author xhl
 * @desc 2019/4/3 13:49
 * TextView帮助类
 */
public class TvTools {
    /**
     * Edittext 首位小数点自动加零，最多两位小数
     *
     * @param editText
     */
    public static void setEdTwoDecimal(EditText editText) {
        setEdDecimal(editText, 2);
    }
    public static void setEdDecimal(EditText editText, int count) {
        if (count < 0) {
            count = 0;
        }

        count += 1;

        editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);

        //设置字符过滤
        final int finalCount = count;
        editText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (".".contentEquals(source) && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int mlength = dest.toString().substring(index).length();
                    if (mlength == finalCount) {
                        return "";
                    }
                }

                if (dest.toString().equals("0") && source.equals("0")) {
                    return "";
                }

                return null;
            }
        }});
    }

    /**
     * 只允许数字和汉字
     * @param editText
     */
    public static void setEdType(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void
            beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void
            onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = editText.getText().toString();
                String str = stringFilter(editable);
                if (!editable.equals(str)) {
                    editText.setText(str);
                    //设置新的光标所在位置
                    editText.setSelection(str.length());
                }
            }

            @Override
            public void
            afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * // 只允许数字和汉字
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilter(String str) throws PatternSyntaxException {

        String regEx = "[^0-9\u4E00-\u9FA5]";//正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }



    /**
     * @param editText       输入框控件
     * @param number         位数
     *                       1 -> 1
     *                       2 -> 01
     *                       3 -> 001
     *                       4 -> 0001
     * @param isStartForZero 是否从000开始
     *                       true -> 从 000 开始
     *                       false -> 从 001 开始
     */
    public static void setEditNumberAuto(final EditText editText, final int number, final boolean isStartForZero) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    setEditNumber(editText, number, isStartForZero);
                }
            }
        });
    }

    /**
     * @param editText       输入框控件
     * @param number         位数
     *                       1 -> 1
     *                       2 -> 01
     *                       3 -> 001
     *                       4 -> 0001
     * @param isStartForZero 是否从000开始
     *                       true -> 从 000 开始
     *                       false -> 从 001 开始
     */
    public static void setEditNumber(EditText editText, int number, boolean isStartForZero) {
        StringBuilder s = new StringBuilder(editText.getText().toString());
        StringBuilder temp = new StringBuilder();

        int i;
        for (i = s.length(); i < number; ++i) {
            s.insert(0, "0");
        }
        if (!isStartForZero) {
            for (i = 0; i < number; ++i) {
                temp.append("0");
            }

            if (s.toString().equals(temp.toString())) {
                s = new StringBuilder(temp.substring(1) + "1");
            }
        }
        editText.setText(s.toString());
    }
}
