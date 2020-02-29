package com.example.flashlight

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager

//플래시를 켜려면 CameraManager 객체기 필요하고 이를 얻으려면 Context객체가 필요하기 때문에
// 생성자로 Context를 받음
class Torch(context: Context) {
    //cameraId 변수를 초기화 하기 전에 getCameraId로 처리해야 하니까 null로 먼저 초기화 한듯
    private var cameraId: String? = null //클래스 초기화 때 카메라 아이디를 받음
    //context의 getSystemService()메서드는 안드로이드 시스템에서 제공하는 각종 서비스를
    //관리하는 매니저 클래스를 생성합니다. 인자로 Contest클래스에 정의된 서비스를 정의한 상수
    //(CAMERA_SERVICE)를 지정함. 이 메서드는 Object형을 반환하기 때문에 as연산자를 사용하여
    //CameraService형으로 형변환을 함
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    init{
        cameraId = getCameraId()
    }

    fun flashOn(){
        cameraManager.setTorchMode(cameraId, true)
    }

    fun flashOff(){
        cameraManager.setTorchMode(cameraId, false)
    }
    //getCameraId()메서드는 카메라가 없다면 ID가 null일 수 있기 때문에 반환 값은 String?로 지정
    private fun getCameraId(): String? {
        val cameraIds = cameraManager.cameraIdList
        for(id in cameraIds){
            val info = cameraManager.getCameraCharacteristics(id)
            val flashAvailable = info.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
            val lensFacing = info.get(CameraCharacteristics.LENS_FACING)
            if(flashAvailable != null
                && flashAvailable
                && lensFacing != null
                && lensFacing == CameraCharacteristics.LENS_FACING_BACK){
                return id
            }
        }
        return null
    }
}