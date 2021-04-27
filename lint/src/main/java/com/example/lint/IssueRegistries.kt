package com.example.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

//@Suppress("UnstableApiUsage")
class IssueRegistries : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(
            MyFirstIssue.ImageSrcIssue
        )

    override val api: Int
        get() = CURRENT_API

}
