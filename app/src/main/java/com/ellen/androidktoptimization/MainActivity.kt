package com.ellen.androidktoptimization

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ellen.androidktoptimization.ifop.Condition

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var a = 25
        var b = 27
        Condition()
            .check(a > b, {
                a = 28
            }){
                Log.e("Ellen2021", "a<b")
            }
            .checkNoCallback(a > 26)
            .checkFalseCallback(a < b) {
                Log.e("Ellen2021", "a等于28时，a<b")
            }.checkTrueCallback(a < b) {
            Log.e("Ellen2021", "a等于28时，a>b")
        }.start()

        if(a>b){
            a = 28
            if(a>26){
                if(a >= b){
                    Log.e("Ellen2021", "a等于28时，a<b")
                    if(a<b){
                        Log.e("Ellen2021", "a等于28时，a>b")
                    }
                }
            }
        }else{
            Log.e("Ellen2021", "a<b")
        }
    }
    
}