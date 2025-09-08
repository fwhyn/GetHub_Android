package com.fwhyn.app.gethub

import org.junit.Test
import org.junit.Assert.*
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.javaMethod
import java.lang.reflect.Modifier

// Dagger/Hilt annotations (compile-time only; we reference their types for reflection)
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

class TestDiMainTest {

    @Test
    fun `module class is annotated with @Module and @InstallIn(ActivityRetainedComponent)`() {
        val kClass = TestDiMain::class

        // @Module present
        val moduleAnn = kClass.findAnnotation<Module>()
        assertNotNull("@Module annotation should be present on TestDiMain", moduleAnn)

        // @InstallIn(ActivityRetainedComponent::class) present and correctly targeted
        val installInAnn = kClass.findAnnotation<InstallIn>()
        assertNotNull("@InstallIn annotation should be present on TestDiMain", installInAnn)
        // Sanity check the component class is among the targets
        val targets = installInAnn\!\!.value
        assertTrue(
            "@InstallIn should target ActivityRetainedComponent",
            targets.any { it == ActivityRetainedComponent::class }
        )
    }

    @Test
    fun `module class is open (non-final) so Hilt can proxy if needed`() {
        val clazz = TestDiMain::class.java
        assertFalse("TestDiMain should be open (non-final)", Modifier.isFinal(clazz.modifiers))
    }

    @Test
    fun `provides function 'test' exists, is public, takes no params, returns expected value`() {
        val instance = TestDiMain()

        // Locate function named 'test'
        val kFunc = TestDiMain::class.declaredFunctions.firstOrNull { it.name == "test" }
        assertNotNull("Expected a function named 'test' on TestDiMain", kFunc)

        // Check visibility/public via Java method modifiers
        val javaMethod = kFunc\!\!.javaMethod
        assertNotNull("KFunction should have a Java method representation", javaMethod)
        assertTrue("Provided function should be public", Modifier.isPublic(javaMethod\!\!.modifiers))

        // No parameters
        assertEquals("Provided function 'test' should have no parameters", 0, kFunc.parameters.size - 1) // subtract instance

        // Annotation @Provides present
        val providesAnn = javaMethod.getAnnotation(Provides::class.java)
        assertNotNull("@Provides annotation should be present on 'test' function", providesAnn)

        // Behavior: should return the expected constant "test"
        val result = instance.test()
        assertEquals("test", result)
        assertTrue("Result should not be blank", result.isNotBlank())
    }
}