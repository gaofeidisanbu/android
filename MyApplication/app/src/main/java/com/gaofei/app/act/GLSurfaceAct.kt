package com.gaofei.app.act

import android.opengl.GLSurfaceView
import android.os.Bundle
import com.gaofei.app.R
import com.gaofei.app.databinding.ActGlSurfaceBinding
import com.gaofei.library.base.BaseAct
import com.gaofei.library.utils.LogUtils
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
private lateinit var binding: ActGlSurfaceBinding



class GLSurfaceAct: BaseAct() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActGlSurfaceBinding.inflate(layoutInflater)
        setContentView(R.layout.act_gl_surface)
        binding.surface.setRenderer(object: GLSurfaceView.Renderer {
            override fun onDrawFrame(gl: GL10?) {
                LogUtils.d(gl)
            }

            override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
                LogUtils.d(gl)
            }

            override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
                LogUtils.d(gl)
            }

        })
    }
}