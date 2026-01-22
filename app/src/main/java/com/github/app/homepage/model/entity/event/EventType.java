package com.github.app.homepage.model.entity.event;

import androidx.annotation.Nullable;

/**
 * com.github.app.events.model.EventType
 *
 * @author Ivan on 2019-12-29
 * @version v0.1
 * @since v1.0
 **/
public enum EventType {
    // 事件类型定义
    CHECK_RUN_EVENT("CheckSuiteEvent", 1001),
    COMMIT_COMMENT_EVENT("CommitCommentEvent", 1002),
    CONTENT_REFERENCE_EVENT("ContentReferenceEvent", 1003),
    CREATE_EVENT("CreateEvent", 1004),
    DELETE_EVENT("DeleteEvent", 1005),
    DEPLOY_KEY_EVENT("DeployKeyEvent", 1006),
    DEPLOYMENT_EVENT("DeploymentEvent", 1007),
    DEPLOYMENT_STATUS_EVENT("DeploymentStatusEvent", 1008),
    DOWNLOAD_EVENT("DownloadEvent", 1009),
    FOLLOW_EVENT("FollowEvent", 1010),
    FORK_EVENT("ForkEvent", 1011),
    FORK_APPLY_EVENT("ForkApplyEvent", 1012),
    GITHUB_APP_AUTHORIZATION_EVENT("GitHubAppAuthorizationEvent", 1013),
    GIST_EVENT("GistEvent", 1014),
    GOLLUM_EVENT("GollumEvent", 1015),
    INSTALLATION_EVENT("InstallationEvent", 1016),
    INSTALLATION_REPOSITORIES_EVENT("InstallationRepositoriesEvent", 1017),
    ISSUE_COMMENT_EVENT("IssueCommentEvent", 1018),
    ISSUES_EVENT("IssuesEvent", 1019),
    LABEL_EVENT("LabelEvent", 1020),
    MARKETPLACE_PURCHASE_EVENT("MarketplacePurchaseEvent", 1021),
    MEMBER_EVENT("MemberEvent", 1022),
    MEMBERSHIP_EVENT("MembershipEvent", 1023),
    META_EVENT("MetaEvent", 1024),
    MILESTONE_EVENT("MilestoneEvent", 1025),
    ORGANIZATION_EVENT("OrganizationEvent", 1026),
    ORG_BLOCK_EVENT("OrgBlockEvent", 1027),
    PACKAGE_EVENT("PackageEvent", 1028),
    PAGE_BUILD_EVENT("PageBuildEvent", 1029),
    PROJECT_CARD_EVENT("ProjectCardEvent", 1030),
    PROJECT_COLUMN_EVENT("ProjectColumnEvent", 1031),
    PROJECT_EVENT("ProjectEvent", 1032),
    PUBLIC_EVENT("PublicEvent", 1033),
    PULL_REQUEST_EVENT("PullRequestEvent", 1034),
    PULL_REQUEST_REVIEW_EVENT("PullRequestReviewEvent", 1035),
    PULL_REQUEST_REVIEW_COMMENT_EVENT("PullRequestReviewCommentEvent", 1036),
    PUSH_EVENT("PushEvent", 1037),
    RELEASE_EVENT("ReleaseEvent", 1038),
    REPOSITORY_DISPATCH_EVENT("RepositoryDispatchEvent", 1039),
    REPOSITORY_EVENT("RepositoryEvent", 1040),
    REPOSITORY_IMPORT_EVENT("RepositoryImportEvent", 1041),
    REPOSITORY_VULNERABILITY_ALERT_EVENT("RepositoryVulnerabilityAlertEvent", 1042),
    SECURITY_ADVISORY_EVENT("SecurityAdvisoryEvent", 1043),
    STAR_EVENT("StarEvent", 1044),
    STATUS_EVENT("StatusEvent", 1045),
    TEAM_EVENT("TeamEvent", 1046),
    TEAM_ADD_EVENT("TeamAddEvent", 1047),
    WATCH_EVENT("WatchEvent", 1048);

    private final String name;
    private final int type;

    EventType(String eventName, int eventType) {
        this.name = eventName;
        this.type = eventType;
    }

    public String eventName() {
        return name;
    }

    public int type() {
        return type;
    }

    @Nullable
    public static EventType fromName(String eventName) {
        for (EventType eventType : values()) {
            if (eventType.name.equals(eventName)) {
                return eventType;
            }
        }
        return null;
    }

    @Nullable
    public static EventType fromType(int eventId) {
        for (EventType eventType : values()) {
            if (eventType.type == eventId) {
                return eventType;
            }
        }
        return null;
    }

    public static boolean contains(String eventName) {
        for (EventType eventType : values()) {
            if (eventType.name.equals(eventName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(int eventId) {
        for (EventType eventType : values()) {
            if (eventType.type == eventId) {
                return true;
            }
        }
        return false;
    }

}
