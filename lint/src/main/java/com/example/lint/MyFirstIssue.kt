package com.example.lint

import com.android.SdkConstants
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Element

@Suppress("UnstableApiUsage")
class MyFirstIssue : ResourceXmlDetector() {
    companion object {
        private const val ImageSrcExplanation = "Please use `app:srcCompat` instead of `android:src` when setting an image resource"

        val ImageSrcIssue = Issue.create(
            id = "ImageViewSrc",
            briefDescription = "ImageView should not use `android:src`",
            explanation = ImageSrcExplanation,
            category = Category.CORRECTNESS,
            severity = Severity.WARNING,
            implementation = Implementation(
                MyFirstIssue::class.java,
                Scope.RESOURCE_FILE_SCOPE
            ),
            androidSpecific = true
        )
    }

    override fun getApplicableElements() = listOf(SdkConstants.IMAGE_VIEW)

    override fun visitElement(context: XmlContext, element: Element) {
        if (element.hasAttributeNS(SdkConstants.ANDROID_URI, SdkConstants.ATTR_SRC)) {
            reportSrcIssue(context, element)
        }
    }

    private fun reportSrcIssue(context: XmlContext, element: Element) {
        context.report(
            ImageSrcIssue,
            element,
            context.getLocation(element.getAttributeNodeNS(
                SdkConstants.ANDROID_URI,
                SdkConstants.ATTR_SRC
            )),
            ImageSrcExplanation,
            LintFix.create().composite(
                LintFix.create().set(
                    SdkConstants.AUTO_URI, SdkConstants.ATTR_SRC_COMPAT,
                    element.getAttributeNS(SdkConstants.ANDROID_URI, SdkConstants.ATTR_SRC)
                ).build(),
                LintFix.create().unset(SdkConstants.ANDROID_URI, SdkConstants.ATTR_SRC).build()
            )
        )
    }

}
