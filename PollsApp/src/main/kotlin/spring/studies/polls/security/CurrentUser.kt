package spring.studies.polls.security

import org.springframework.security.core.annotation.AuthenticationPrincipal
import java.lang.annotation.Documented

@Target(allowedTargets = [AnnotationTarget.TYPE,AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE_PARAMETER]) // This meta-annotation specifies which code elements this annotation may refer to
@Retention(AnnotationRetention.RUNTIME)
@Documented
@AuthenticationPrincipal // from spring security. it access the currently authenticated user in the controllers.
annotation class CurrentUser
/*
*
* this annotation is a wrapper around @AuthenticationPrincipal, because if we decide to remove Spring Security from our project, we can easily do it by simply changing the CurrentUser annotation
*
* */