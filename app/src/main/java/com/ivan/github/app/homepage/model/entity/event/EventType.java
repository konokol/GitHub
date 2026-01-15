package com.ivan.github.app.homepage.model.entity.event;

/**
 * com.ivan.github.app.events.model.EventType
 *
 * @author Ivan on 2019-12-29
 * @version v0.1
 * @since v1.0
 **/
public interface EventType {

    String CHECK_RUN_EVENT = "CheckSuiteEvent";
    String COMMIT_COMMENT_EVENT = "CommitCommentEvent";
    String CONTENT_REFERENCE_EVENT = "ContentReferenceEvent";
    String CREATE_EVENT = "CreateEventPayload";
    String DELETE_EVENT = "DeleteEvent";
    String DEPLOY_KEY_EVENT = "DeployKeyEvent";
    String DEPLOYMENT_EVENT = "DeploymentEvent";
    String DEPLOYMENT_STATUS_EVENT = "DeploymentStatusEvent";
    String DOWNLOAD_EVENT = "DownloadEvent";
    String FOLLOW_EVENT = "FollowEvent";
    String FORK_EVENT = "ForkEvent";
    String FORK_APPLY_EVENT = "ForkApplyEvent";
    String GITHUB_APP_AUTHORIZATION_EVENT = "GitHubAppAuthorizationEvent";
    String GIST_EVENT = "GistEvent";
    String GOLLUM_EVENT = "GollumEvent";
    String INSTALLATION_EVENT = "InstallationEvent";
    String INSTALLATION_REPOSITORIES_EVENT = "InstallationRepositoriesEvent";
    String ISSUE_COMMENT_EVENT = "IssueCommentEvent";
    String ISSUES_EVENT = "IssuesEvent";
    String LABEL_EVENT = "LabelEvent";
    String MARKETPLACE_PURCHASE_EVENT = "MarketplacePurchaseEvent";
    String MEMBER_EVENT = "MemberEvent";
    String MEMBERSHIP_EVENT = "MembershipEvent";
    String META_EVENT = "MetaEvent";
    String MILESTONE_EVENT = "MilestoneEvent";
    String ORGANIZATION_EVENT = "OrganizationEvent";
    String ORG_BLOCK_EVENT = "OrgBlockEvent";
    String PACKAGE_EVENT = "PackageEvent";
    String PAGE_BUILD_EVENT = "PageBuildEvent";
    String PROJECT_CARD_EVENT = "ProjectCardEvent";
    String PROJECT_COLUMN_EVENT = "ProjectColumnEvent";
    String PROJECT_EVENT = "ProjectEvent";
    String PUBLIC_EVENT = "PublicEvent";
    String PULL_REQUEST_EVENT = "PullRequestEvent";
    String PULL_REQUEST_REVIEW_EVENT = "PullRequestReviewEvent";
    String PULL_REQUEST_REVIEW_COMMENT_EVENT = "PullRequestReviewCommentEvent";
    String PUSH_EVENT = "PushEvent";
    String RELEASE_EVENT = "ReleaseEvent";
    String REPOSITORY_DISPATCH_EVENT = "RepositoryDispatchEvent";
    String REPOSITORY_EVENT = "RepositoryEvent";
    String REPOSITORY_IMPORT_EVENT = "RepositoryImportEvent";
    String REPOSITORY_VULNERABILITY_ALERT_EVENT = "RepositoryVulnerabilityAlertEvent";
    String SECURITY_ADVISORY_EVENT = "SecurityAdvisoryEvent";
    String STAR_EVENT = "StarEvent";
    String STATUS_EVENT = "StatusEvent";
    String TEAM_EVENT = "TeamEvent";
    String TEAM_ADD_EVENT = "TeamAddEvent";
    String WATCH_EVENT = "WatchEvent";
}
