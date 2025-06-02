package com.morpheusdata.model;

/**
 * ProcessStepType for use with a {@link ProcessEvent}
 * <p>
 * This is an improved version of ProcessType that provides more flexibility for custom process types defined in plugins.
 */
public class ProcessStepType {
	/**
	 * Pre-defined constants based on what's defined in core, use these when you need to refer to a core process type
	 * for API calls in {@link com.morpheusdata.core.MorpheusProcessService}
	 */
	public static final ProcessStepType ANSIBLE_COMMAND = forCode("ansibleCommand");
	public static final ProcessStepType ANSIBLE_INSTALL = forCode("ansibleInstall");
	public static final ProcessStepType ANSIBLE_PLAYBOOK = forCode("ansiblePlaybook");
	public static final ProcessStepType ANSIBLE_PROVISION = forCode("ansibleProvision");
	public static final ProcessStepType ANSIBLE_REPO = forCode("ansibleRepo");
	public static final ProcessStepType ANSIBLE_TOWER_INVENTORY = forCode("ansibleTowerInventory");
	public static final ProcessStepType ANSIBLE_TOWER_JOB_LAUNCH = forCode("ansibleTowerJobLaunch");
	public static final ProcessStepType ANSIBLE_TOWER_JOB_TEMPLATE = forCode("ansibleTowerJobTemplate");
	public static final ProcessStepType ANSIBLE_TOWER_PROVISION = forCode("ansibleTowerProvision");
	public static final ProcessStepType APPLY_PACKAGE = forCode("applyPackage");
	public static final ProcessStepType APPLY_RESOURCE_SPEC = forCode("applyResourceSpec");
	public static final ProcessStepType APP_PROVISION = forCode("appProvision");
	public static final ProcessStepType APP_TERRAFORM_COMMAND = forCode("appTerraformCommand");
	public static final ProcessStepType AZURE_ARM_PROVISION = forCode("azureArmProvision");
	public static final ProcessStepType AZURE_OPERATION = forCode("azureOperation");
	public static final ProcessStepType CHEF_BOOTSTRAP = forCode("chefBootstrap");
	public static final ProcessStepType CHEF_INSTALL = forCode("chefInstall");
	public static final ProcessStepType CHEF_PROVISION = forCode("chefProvision");
	public static final ProcessStepType CHEF_RUN = forCode("chefRun");
	public static final ProcessStepType CLONING = forCode("cloning");
	public static final ProcessStepType CONFIGURE_RESOURCES = forCode("configureResources");
	public static final ProcessStepType CONTAINER_SCRIPT = forCode("containerScript");
	public static final ProcessStepType CONTAINER_TASK = forCode("containerTask");
	public static final ProcessStepType CONTAINER_TEMPLATE = forCode("containerTemplate");
	public static final ProcessStepType CONTAINER_WORKFLOW = forCode("containerWorkflow");
	public static final ProcessStepType DELETE_PACKAGE = forCode("deletePackage");
	public static final ProcessStepType DELETESNAPSHOT = forCode("deletesnapshot");
	public static final ProcessStepType DEPLOY = forCode("deploy");
	public static final ProcessStepType DEPLOY_FILES = forCode("deployFiles");
	public static final ProcessStepType DEPLOY_PACKAGE = forCode("deployPackage");
	public static final ProcessStepType DEPLOY_SCANNER = forCode("deployScanner");
	public static final ProcessStepType DEPLOY_START_INSTANCE = forCode("deployStartInstance");
	public static final ProcessStepType DEPLOY_STOP_INSTANCE = forCode("deployStopInstance");
	public static final ProcessStepType EXECUTE_ACTION = forCode("executeAction");
	public static final ProcessStepType EXECUTE_COMMAND = forCode("executeCommand");
	public static final ProcessStepType EXECUTE_SCAN = forCode("executeScan");
	public static final ProcessStepType EXECUTE_SCRIPT = forCode("executeScript");
	public static final ProcessStepType EXECUTE_TASK = forCode("executeTask");
	public static final ProcessStepType EXECUTE_TEMPLATE = forCode("executeTemplate");
	public static final ProcessStepType EXECUTE_WORKFLOW = forCode("executeWorkflow");
	public static final ProcessStepType EXTRACT_RESULTS = forCode("extractResults");
	public static final ProcessStepType GENERAL = forCode("general");
	public static final ProcessStepType GUEST_CUSTOMIZATIONS = forCode("guestCustomizations");
	public static final ProcessStepType INSTANCE_ACTION = forCode("instanceAction");
	public static final ProcessStepType INSTANCE_TASK = forCode("instanceTask");
	public static final ProcessStepType INSTANCE_TERRAFORM_COMMAND = forCode("instanceTerraformCommand");
	public static final ProcessStepType INSTANCE_WORKFLOW = forCode("instanceWorkflow");
	public static final ProcessStepType LOCAL_TASK = forCode("localTask");
	public static final ProcessStepType LOCAL_WORKFLOW = forCode("localWorkflow");
	public static final ProcessStepType PLAN_RESOURCES = forCode("planResources");
	public static final ProcessStepType POST_PROVISION = forCode("postProvision");
	public static final ProcessStepType PROVISION = forCode("provision");
	public static final ProcessStepType PROVISION_AGENT = forCode("provisionAgent");
	public static final ProcessStepType PROVISION_APP_DEPLOY = forCode("provisionAppDeploy");
	public static final ProcessStepType PROVISION_CLOUD_INIT = forCode("provisionCloudInit");
	public static final ProcessStepType PROVISION_CONFIG = forCode("provisionConfig");
	public static final ProcessStepType PROVISION_DEPLOY = forCode("provisionDeploy");
	public static final ProcessStepType PROVISION_FINALIZE = forCode("provisionFinalize");
	public static final ProcessStepType PROVISION_IMAGE = forCode("provisionImage");
	public static final ProcessStepType PROVISION_INSTANCES = forCode("provisionInstances");
	public static final ProcessStepType PROVISION_ITEM = forCode("provisionItem");
	public static final ProcessStepType PROVISION_ITEMS = forCode("provisionItems");
	public static final ProcessStepType PROVISION_LAUNCH = forCode("provisionLaunch");
	public static final ProcessStepType PROVISION_NETWORK = forCode("provisionNetwork");
	public static final ProcessStepType PROVISION_RESIZE = forCode("provisionResize");
	public static final ProcessStepType PROVISION_RESOLVE = forCode("provisionResolve");
	public static final ProcessStepType PROVISION_RESOURCES = forCode("provisionResources");
	public static final ProcessStepType PROVISION_STATE = forCode("provisionState");
	public static final ProcessStepType PROVISION_STATE_REFRESH = forCode("provisionStateRefresh");
	public static final ProcessStepType PROVISION_UPDATES = forCode("provisionUpdates");
	public static final ProcessStepType PROVISION_VOLUMES = forCode("provisionVolumes");
	public static final ProcessStepType RECONFIGURE = forCode("reconfigure");
	public static final ProcessStepType RESIZE = forCode("resize");
	public static final ProcessStepType RESIZE_MEMORY = forCode("resizeMemory");
	public static final ProcessStepType RESIZE_START = forCode("resizeStart");
	public static final ProcessStepType RESIZE_STOP_INSTANCE = forCode("resizeStopInstance");
	public static final ProcessStepType RESIZE_VOLUMES = forCode("resizeVolumes");
	public static final ProcessStepType RESOURCE_CONFIG = forCode("resourceConfig");
	public static final ProcessStepType REVERT = forCode("revert");
	public static final ProcessStepType SALT_COMMAND = forCode("saltCommand");
	public static final ProcessStepType SALT_EXECUTE = forCode("saltExecute");
	public static final ProcessStepType SALT_INSTALL = forCode("saltInstall");
	public static final ProcessStepType SALT_MINION = forCode("saltMinion");
	public static final ProcessStepType SALT_PROVISION = forCode("saltProvision");
	public static final ProcessStepType SALT_STATE = forCode("saltState");
	public static final ProcessStepType SECURITY_SCAN = forCode("securityScan");
	public static final ProcessStepType SERVER_GROUP_PROVISION = forCode("serverGroupProvision");
	public static final ProcessStepType SERVER_GROUP_WORKFLOW = forCode("serverGroupWorkflow");
	public static final ProcessStepType SERVER_PROVISION = forCode("serverProvision");
	public static final ProcessStepType SERVER_SCRIPT = forCode("serverScript");
	public static final ProcessStepType SERVER_TASK = forCode("serverTask");
	public static final ProcessStepType SERVER_WORKFLOW = forCode("serverWorkflow");
	public static final ProcessStepType SHUTDOWN = forCode("shutdown");
	public static final ProcessStepType SNAPSHOT = forCode("snapshot");
	public static final ProcessStepType STARTUP = forCode("startup");
	public static final ProcessStepType TEARDOWN = forCode("teardown");
	public static final ProcessStepType TERRAFORM_COMMAND = forCode("terraformCommand");
	public static final ProcessStepType UPDATE_PACKAGE = forCode("updatePackage");
	public static final ProcessStepType UPGRADE_PACKAGE = forCode("upgradePackage");

	public String name;
	public String description;
	public String code;

	/**
	 * Static constructor for use when only a code is needed
	 * @param code unique code of the ProcessStepType
	 * @return process step type
	 */
	public static ProcessStepType forCode(String code) {
		ProcessStepType stepType = new ProcessStepType();
		stepType.code = code;
		return stepType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return code;
	}
}
