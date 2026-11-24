package jiamin.chen.orangecloud.core.auth

import jiamin.chen.orangecloud.BuildConfig

/**
 * OAuth 配置（与 iOS Core/Auth/OAuthConfig.swift 对应）。
 * clientId 经 Gradle 按风味注入：play = 官方 Client，oss = 自编译者自填。
 * Cloudflare OAuth 只接受 https redirect，指向 Web 后端回调中转，再 302 跳回自定义 scheme。
 */
object OAuthConfig {
    /** 官方 Client 为 PKCE 公开客户端（非机密）；oss 风味默认空串。 */
    val clientId: String = BuildConfig.OAUTH_CLIENT_ID

    const val CALLBACK_SCHEME = "orangecloud"
    const val CALLBACK_HOST = "oauth"

    /** 官方中转（play / direct 用）。oss 自编译者换成自己部署的中转，见 build.gradle.kts 的 OAUTH_REDIRECT_URI。 */
    private const val OFFICIAL_REDIRECT_URI = "https://o-c.do/oauth/callback"

    /**
     * 必须与该 Client 在 Cloudflare 后台登记的 redirect_uri **逐字一致**，否则授权端点直接 302 到
     * /oauth/error?error=invalid_request（页面上表现为「路由 /oauth/error 不存在」）。
     */
    val REDIRECT_URI: String = BuildConfig.OAUTH_REDIRECT_URI.ifEmpty { OFFICIAL_REDIRECT_URI }

    const val AUTHORIZATION_URL = "https://dash.cloudflare.com/oauth2/auth"
    /** 网页登出端点：添加账号时先登出再续跳授权页，避免复用上一个登录态（支持 ?to= 续跳）。 */
    const val LOGOUT_URL = "https://dash.cloudflare.com/logout"
    const val TOKEN_URL = "https://dash.cloudflare.com/oauth2/token"
    const val REVOKE_URL = "https://dash.cloudflare.com/oauth2/revoke"
    const val USERINFO_URL = "https://dash.cloudflare.com/oauth2/userinfo"
}
