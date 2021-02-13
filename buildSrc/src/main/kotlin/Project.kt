import io.codearte.gradle.nexus.NexusStagingExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugins.signing.SigningExtension

/*
 * (c) 2020-2021 SorrowBlue.
 */

fun Project.nexusStaging(function: NexusStagingExtension.() -> Unit) = function(extensions.getByType())

fun Project.publishing(function: PublishingExtension.() -> Unit) =
    function(extensions.getByType())

fun Project.signing(function: SigningExtension.() -> Unit) = function(extensions.getByType())

val Project.publishing
    get() = extensions.getByType<PublishingExtension>()

fun Project.ext(configure: Action<ExtraPropertiesExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("ext", configure)

val Project.ext get() = extensions.getByType<ExtraPropertiesExtension>()
