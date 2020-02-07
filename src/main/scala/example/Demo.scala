package example

import glm_.vec2.{Vec2, Vec2i}
import glm_.vec4.Vec4
import gln.GlnKt.{glClearColor, glViewport}
import imgui.classes.Context
import imgui.impl.gl.{CommonGLKt, ImplGL3}
import imgui.impl.glfw.ImplGlfw
import imgui.{ImGui, ImguiKt, MutableProperty0}
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.{GL_COLOR_BUFFER_BIT, glClear}
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil.NULL
import uno.glfw.{GlfwWindow, VSync, glfw, windowHint}

object Demo extends Greeting with App {
  val f = Array(0f)
  val counter: Array[Int] = Array(0)

  val clearColor = new Vec4(0.45f, 0.55f, 0.6f, 1f)

  val showDemo = Array(x = false)
  val showAnotherWindow = new MutableProperty0[java.lang.Boolean](false)
  GLFW.glfwSetErrorCallback((error: Int, description: Long) =>
    println(s"Error $error $description"))
  glfw.INSTANCE.init()

  val imgui = ImGui.INSTANCE

  windowHint.INSTANCE.setDebug(ImguiKt.DEBUG)
  CommonGLKt.setGlslVersion(130)
  windowHint.INSTANCE.getContext.setVersion("3.0")

  val window = new GlfwWindow(1280, 720, "Hello", NULL, new Vec2i((30)), true)
  window.makeContextCurrent()
  glfw.INSTANCE.setSwapInterval(VSync.ON)
  GL.createCapabilities()

  val context = new Context();
  imgui.styleColorsDark(null)

  val implGlfw = new ImplGlfw(window, true, null)
  val implGl3 = new ImplGL3

  val io = imgui.getIo

  window.loop(new kotlin.jvm.functions.Function1[MemoryStack, kotlin.Unit] {
    override def invoke(p1: MemoryStack): kotlin.Unit = mainLoop(p1)
  })

  implGlfw.shutdown()
  implGl3.shutdown()
  context.destroy()
  window.destroy()

  def mainLoop(memoryStack: MemoryStack): kotlin.Unit = {

    implGl3.newFrame()
    implGlfw.newFrame()

    imgui.newFrame()

    imgui.text("Hello World")
    imgui.sliderFloat("float", f, 0, 0f, 1f, "%3f", 1f)
    imgui.colorEdit3("clear color", clearColor, 0)

    imgui.checkbox("Demo Window", showDemo);
    imgui.checkbox("Another Window", showAnotherWindow);

    if (imgui.button("Button", new Vec2()))
      counter(0) += 1

    imgui.sameLine(0f, -1f)
    imgui.text("counter = " + counter(0))

    imgui.text("Application average %.3f ms/frame (%.1f FPS)", 1_000f / io.getFramerate, io.getFramerate)

    if (showAnotherWindow.get) {
      imgui.begin("Another Window", showAnotherWindow, 0)
      imgui.text("Hello from another window!")
      if (imgui.button("Close Me", new Vec2)) showAnotherWindow.set(false)
      imgui.end()
    }
    if (showDemo(0)) {
      imgui.showDemoWindow(showDemo)
    }

    imgui.render()
    glViewport(window.getFramebufferSize)
    glClearColor(clearColor)
    glClear(GL_COLOR_BUFFER_BIT)
    implGl3.renderDrawData(imgui.getDrawData)
    kotlin.Unit.INSTANCE
  }
}

trait Greeting {}
