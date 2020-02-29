package com.example.flashlight

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TorchService : Service() {
    //TorchService가 Torch 클래스를 사용해야 함
    //by lazy를 사용하여 torch 객체를 처음 사용할 때 초기화함. onCreate 콜백 메서드를 사용하는 것보다 짦음
    private val torch: Torch by lazy{
        Torch(this)
    }
    private var isRunning = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            // 앱에서 실행할 경우
            "on"-> {
                torch.flashOn()
                isRunning = true
            }
            "off"->{
                torch.flashOff()
                isRunning = false
            }
            //서비스에서 실행할 경우
            else->{
                if(isRunning){
                    torch.flashOn()
                }else{
                    torch.flashOff()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
