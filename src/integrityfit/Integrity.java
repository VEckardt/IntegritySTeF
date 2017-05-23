/*
 * This class 
 * and open the template in the editor.
 */
package integrityfit;

// import the fitnesse classes
import fit.*;

/**
 * Provides the main FitNesse test routine
 *
 * @author veckardt
 */
public class Integrity extends ColumnFixture {

    // IntegrityFitNesse extends IntegrityAPI
    private IntegrityFitNesse im;

    /**
     * Constructor to open/retrieve the Integrity session
     *
     * @param host can be host or host:port
     */
    private Integrity(String hostAndPort) {
        // Connect to Integrity
        im = new IntegrityFitNesse(hostAndPort, 0, null, null);
        // im.setCommandsToExecute("*");   
        // im.debugOn();
    }

    public Integrity(String hostAndPort, String userAndPassword) {
        // Connect to Integrity
        im = new IntegrityFitNesse(hostAndPort, 0, userAndPassword, null);
        // im.setCommandsToExecute("*");   
        // im.debugOn();
    }

    // replace html
    private String replaceHtml(String text) {
        // return text.replaceAll("\\<[^>]*>", "");
        return text.replaceAll("\\<.*?>", "").replace("[?]", "").replace("[?]", "");
    }

    /**
     * Different Set Methods
     */
    public void setModule(String text) {
        im.setModule(text);
    }

    ;
    public void setCommand(String text) {
        im.setCommand(text);
    }

    public void setEnvironment(String text) {
        im.setEnvironment(text);
    }

    /**
     * Values, which will be treated as params alway
     *
     * @param text
     */
    public void setType(String text) {
        im.setParam(Config.fnType, text);
    }

    public void setFilterQueryDefinition(String text) {
        im.setParam("filterQueryDefinition", text);
    }

    public void setAddRelationships(String text) {
        im.setParam("addRelationships", text);
    }

    public void setConfig(String text) {
        im.setParam("config", text);
    }

    public void setFile(String text) {
        im.setFileName(text);
        im.setParam("file", text);
    }

    public void setOutputFile(String text) {
        im.setFileName(text);
        im.setParam("outputFile", text);
    }

    public void setQueryDefinition(String text) {
        im.setParam("queryDefinition", text);
    }

    public void setRemoveRelationships(String text) {
        im.setParam("removeRelationships", text);
    }

    public void setQuery(String text) {
        im.setParam("query", text);
    }

    public void setIssues(String text) {
        im.setParam("issues", text);
    }

    public void setDisplayRows(String text) {
        im.setField("displayRows", text);
    }
    //richContent

    public void setRichContent(String text) {
        im.setField("richContent", text);
    }
    // sharedAdmin

    public void setSharedAdmin(String text) {
        im.setField("sharedAdmin", text);
    }

    public void setDefaultColumns(String text) {
        im.setField("defaultColumns", text);
    }

    public void setMultiValued(String text) {
        im.setField("multiValued", text);
    }

    public void setForward(String text) {
        // im.setField("forward", text);
        // seams to be no more relavant at all
    }
    // setVisibleFields

    public void setVisibleFields(String text) {
        im.setField("visibleFields", text);
    }
    // Allowed Types

    public void setAllowedTypes(String text) {
        im.setField("allowedTypes", text);
    }

    public void setCorrelatedFields(String text) {
        im.setField("correlation", text);
    }

    public void setName(String text) {
        im.setField("name", text);
    }

    ;
    // displayStyle
    public void setDisplayStyle(String text) {
        im.setField("displayStyle", text);
    }

    ;
    
    
    public void setDisplayName(String text) {
        im.setField("displayname", text);
    }

    ;    
    public void setDefaultAttachmentField(String text) {
        im.setField("defaultAttachmentField", text);
    }

    ;    
            
    /**
     * Fields, which will be treated as Fields only
     * @param text
     */
    public void setSummary(String text) {
        im.setField(Config.fnSummary, text);
    }

    ;
    public void setDescription(String text) {
        im.setField("Description", text);
    }

    ;
    public void setState(String text) {
        im.setField(Config.fnState, text);
    }

    ;
    public void setProject(String text) {
        im.setField("Project", replaceHtml(text));
    }

    ;
    public void setStartOfReleaseDevelopmentActivities(String text) {
        im.setField("Start of Release Development activities", text);
    }

    ;
    public void setCustomerReleaseDate(String text) {
        im.setField("Customer Release Date", text);
    }

    ;
    public void setReleaseFor(String text) {
        im.setField("Release For", text);
    }

    ;
    public void setReleaseType(String text) {
        im.setField("Release Type", text);
    }

    ;    
    // setActiveRelease
    public void setActiveRelease(String text) {
        im.setField("Active Release", text);
    }

    ;           
    public void setPriority(String text) {
        im.setField("Priority", text);
    }

    ;           
    public void setSafetyRelevant(String text) {
        im.setField("Safety Relevant", text);
    }

    ;     
    public void setRequestorType(String text) {
        im.setField("Requestor Type", text);
    }

    ; 
    public void setChangeConcernType(String text) {
        im.setField("Change Concern Type", text);
    }

    ; 
    public void setArea(String text) {
        im.setField("Area", text);
    }

    ; 
    public void setChangeOrConcernOriginalID(String text) {
        im.setField("Change or Concern Original ID", text);
    }

    ;   
    // CCB Comment
    public void setCCBComment(String text) {
        im.setField("CCB Comment", text);
    }

    ; 
    // Analysis, Influence On Requirements
    public void setAnalysis(String text) {
        im.setField("Analysis", text);
    }

    ;   
    public void setInfluenceOnRequirements(String text) {
        im.setField("Influence On Requirements", text);
    }

    ;   
    //setTaskType
    public void setTaskType(String text) {
        im.setField("Task Type", text);
    }

    ;     
    //setTaskType
    public void setHint(String text) {
        // just as hint column, no assignment please! 
    }

    ;     
    // setReviewer
    public void setReviewer(String text) {
        im.setField("Reviewer", text);
    }

    ;    
    public void setReviewState(String text) {
        im.setField("Review State", text);
    }

    ;    
    public void setReviewComment(String text) {
        im.setField("Review Comment", text);
    }

    ;    
    // Introduced During
    public void setIntroducedDuring(String text) {
        im.setField("Introduced During", text);
    }

    ;    
    public void setText(String text) {
        im.setField(Config.fnText, text);
        // im.setParam(fnText, text);
    }

    ;  
    // setDocumentShortTitle
    public void setDocumentShortTitle(String text) {
        im.setField("Document Short Title", text);
    }

    ;    
    public void setCategory(String text) {
        im.setField("Category", text);
    }

    ;    
    // $IDCC
    public void setAssignedUser(String text) {
        im.setField("Assigned User", text);
    }

    ; 
    //setTestObjective
    public void setTestObjective(String text) {
        im.setField("Test Objective", text);
    }

    ;     
    public void setTests(String text) {
        im.setField("Tests", text);
    }

    ;
    public void setItemID(String text) {
        im.setField("ItemID", text);
    }

    ;    
    public void setRelationship(String text) {
        im.setField("Relationship", text);
    }

    ;
    
    /**
     * Other Common fields
     * @param text
     */
    public void setSelection(String text) {
        im.setSelection(text);
    }

    ;
    public void setID(String text) {
        im.setId(text);
        im.setField("ID", text);
    }

    ; 
    public void setParentID(String text) {
        im.setParentId(text);
    }

    ;   
    public void setContainedBy(String text) {
        im.setParentId(text);  // im.setField("ParentID", text);    }; 
    }

    public void setFieldName(String text) {
        im.setFieldName(text);
    }
    // Test Defect Link

    public void setSessionId(String text) {
        im.setParam("SessionId", text);
    }

    public void setCaseId(String text) {
        im.setParam("CaseId", text);
    }

    public void setDefectId(String text) {
        im.setParam("DefectId", text);
    }
    // for TM

    public void setVerdict(String text) {
        im.setVerdict(text);
    }

    public void setAnnotation(String text) {
        im.setAnnotation(text);
    }

    public void setStepId(String text) {
        im.setStepId(text);
    }

    public void setStepVerdict(String text) {
        im.setStepVerdict(text);
    }

    /**
     * Main ExecutionMethod, returning a return code such as -1, 0
     *
     * @return If successfully 0, otherwise -1
     */
    public String Result() {
        if (im.getCommand().equals("stepresult")) {
            return "0";
        }
        return Integer.toString(im.execute());
    }
//    public String containsFile() {
//        return im.containsFile();
//    }

    ;

    /**
     * Return the last message the Result has initiated
     * @return the message
     */
    public String Message() {
        // im.disconnect();
        return im.getLastMessage();
    }

    /**
     * Returns a specific field value such as type name, project etc.
     *
     * @return the value
     */
    public String Value() {
        return im.getFieldValue();
    }

    /**
     * Finds an Item with a dedicated type, summary and state. Please set type,
     * summary and/or state in advance
     *
     * @return Item ID
     */
    public String findItem() {
        return im.findItem();
    }

    /**
     * Finds an Item with a dedicated type, summary and state. Please set type,
     * summary and/or state in advance
     *
     * @return Item ID
     */
    public String Filesize() {
        return im.getFileSize();
    }

    /**
     * Returns the number of rows (items) or lines (file) returned
     *
     * @return the count
     */
    public String Count() {
        return Integer.toString(im.getCount());
    }

    /**
     * This function is called by end of each fitnesse table
     */
    public void endTable() {
        im.log("Calling endTable (disconnect) ...");
        im.releaseCmdRunner();
    }

    public void setCreatedBy(String text) {
        im.setField("Created By", text);
    }

    ; 
public void setCreatedDate(String text) {
        im.setField("Created Date", text);
    }

    ; 
public void setModifiedBy(String text) {
        im.setField("Modified By", text);
    }

    ; 
public void setModifiedDate(String text) {
        im.setField("Modified Date", text);
    }

    ; 
// public void setSummary(String text) {        im.setField("Summary", text);    }; 
// public void setState(String text) {        im.setField("State", text);    }; 
// public void setAssignedUser(String text) {        im.setField("Assigned User", text);    }; 

public void setLiveItemID(String text) {im.setField("Live Item ID", text);}
public void setAssignedGroup(String text) {im.setField("Assigned Group", text);}
public void setForwardRelationships(String text) {im.setField("Forward Relationships", text);}
public void setBackwardRelationships(String text) {im.setField("Backward Relationships", text);}
public void setSignedBy(String text) {im.setField("Signed By", text);}
public void setSignatureComment(String text) {im.setField("Signature Comment", text);}
public void setAttachments(String text) {im.setField("Attachments", text);}
public void setContains(String text) {im.setField("Contains", text);}
public void setReferences(String text) {im.setField("References", text);}
public void setReferencedBy(String text) {im.setField("Referenced By", text);}
public void setReferenceMode(String text) {im.setField("Reference Mode", text);}
public void setRootID(String text) {im.setField("Root ID", text);}
public void setInputRevisionDate(String text) {im.setField("Input Revision Date", text);}
public void setDocumentID(String text) {im.setField("Document ID", text);}
public void setSubsegmentName(String text) {im.setField("Subsegment Name", text);}
public void setReferencedItemType(String text) {im.setField("Referenced Item Type", text);}
public void setTestsFor(String text) {im.setField("Tests For", text);}
public void setSharedTestSteps(String text) {im.setField("Shared Test Steps", text);}
public void setTestCases(String text) {im.setField("Test Cases", text);}
public void setSharedCategory(String text) {im.setField("Shared Category", text);}
public void setParameters(String text) {im.setField("Parameters", text);}
public void setParameterValues(String text) {im.setField("Parameter Values", text);}
public void setShares(String text) {im.setField("Shares", text);}
public void setSharedBy(String text) {im.setField("Shared By", text);}
public void setTestSteps(String text) {im.setField("Test Steps", text);}
public void setTestsAsOfDate(String text) {im.setField("Tests As Of Date", text);}
public void setBookmarks(String text) {im.setField("Bookmarks", text);}
public void setReferencedBookmarks(String text) {im.setField("Referenced Bookmarks", text);}
public void setRevision(String text) {im.setField("Revision", text);}
public void setRevisionIncrementDate(String text) {im.setField("Revision Increment Date", text);}
public void setSignificantEditDate(String text) {im.setField("Significant Edit Date", text);}
public void setSignificantChangeSinceItemRevision(String text) {im.setField("Significant Change Since Item Revision", text);}
public void setItemSignificantEditDateonSharedItem(String text) {im.setField("Item Significant Edit Date on Shared Item", text);}
public void setReviewSessions(String text) {im.setField("Review Sessions", text);}
public void setReviewSessionFor(String text) {im.setField("Review Session For", text);}
public void setCommentText(String text) {im.setField("Comment Text", text);}
public void setInSession(String text) {im.setField("In Session", text);}
public void setCommentFor(String text) {im.setField("Comment For", text);}
public void setIsApprovalComment(String text) {im.setField("Is Approval Comment", text);}
public void setSelectionIndex(String text) {im.setField("Selection Index", text);}
public void setMajorVersionID(String text) {im.setField("Major Version ID", text);}
public void setMinorVersionID(String text) {im.setField("Minor Version ID", text);}
public void setCustomFields(String text) {im.setField("Custom Fields", text);}
public void setCustomFieldValues(String text) {im.setField("Custom Field Values", text);}
public void setSharedAttachments(String text) {im.setField("Shared Attachments", text);}
public void setAuthorizingChangeRequest(String text) {im.setField("Authorizing Change Request", text);}
public void setTotalChangeRequestCount(String text) {im.setField("Total Change Request Count", text);}
public void setPendingChangeRequestCount(String text) {im.setField("Pending Change Request Count", text);}
public void setClosedChangeRequestCountforProject(String text) {im.setField("Closed Change Request Count for Project", text);}
public void setGlobalInactiveProjectCount(String text) {im.setField("Global Inactive Project Count", text);}
public void setGlobalActiveProjectCount(String text) {im.setField("Global Active Project Count", text);}
public void setGlobalCompletedProjectCount(String text) {im.setField("Global Completed Project Count", text);}
public void setSolutionChangeRequestCountInActiveProjects(String text) {im.setField("Solution Change Request Count In Active Projects", text);}
public void setChangeRequestCountInActiveProjects(String text) {im.setField("Change Request Count In Active Projects", text);}
public void setDocumentCountInActiveProjects(String text) {im.setField("Document Count In Active Projects", text);}
public void setGlobalDocumentCount(String text) {im.setField("Global Document Count", text);}
public void setContentCountInActiveProjects(String text) {im.setField("Content Count In Active Projects", text);}
public void setGlobalContentCount(String text) {im.setField("Global Content Count", text);}
public void setAuthorReferenceCountInActiveProjects(String text) {im.setField("Author Reference Count In Active Projects", text);}
public void setReuseReferenceCountInActiveProjects(String text) {im.setField("Reuse Reference Count In Active Projects", text);}
public void setShareReferenceCountInActiveProjects(String text) {im.setField("Share Reference Count In Active Projects", text);}
public void setSuspectCount(String text) {im.setField("Suspect Count", text);}
public void setTopLevelDocumentCount(String text) {im.setField("Top Level Document Count", text);}
public void setDocumentsAddedYesterdayCount(String text) {im.setField("Documents Added Yesterday Count", text);}
public void setDocumentsModifiedYesterdayCount(String text) {im.setField("Documents Modified Yesterday Count", text);}
public void setAverageDocumentContentCount(String text) {im.setField("Average Document Content Count", text);}
public void setMaximumDocumentContentCount(String text) {im.setField("Maximum Document Content Count", text);}
public void setOrphanedNodeCount(String text) {im.setField("Orphaned Node Count", text);}
public void setOrphanedSharedItemCount(String text) {im.setField("Orphaned Shared Item Count", text);}
public void setChangeRequestAuthorizationsInEffect(String text) {im.setField("Change Request Authorizations In Effect", text);}
public void setShowInFinder(String text) {im.setField("Show In Finder", text);}
public void setRootDocument(String text) {im.setField("Root Document", text);}
public void setSubmitForCI(String text) {im.setField("Submit For CI", text);}
public void setNeedsNewChanges(String text) {im.setField("Needs New Changes", text);}
public void setIncludeReference(String text) {im.setField("Include Reference", text);}
public void setAllowScopeExpansion(String text) {im.setField("Allow Scope Expansion", text);}
public void setSharedText(String text) {im.setField("Shared Text", text);}
public void setChangesAuthorized(String text) {im.setField("Changes Authorized", text);}
public void setDiscussion(String text) {im.setField("Discussion", text);}
public void setDomain(String text) {im.setField("Domain", text);}
public void setEffort(String text) {im.setField("Effort", text);}
public void setRisk(String text) {im.setField("Risk", text);}
public void setSatisfiedBy(String text) {im.setField("Satisfied By", text);}
public void setAuthorizesChangesTo(String text) {im.setField("Authorizes Changes To", text);}
public void setValidatedBy(String text) {im.setField("Validated By", text);}
public void setDocumentedBy(String text) {im.setField("Documented By", text);}
public void setSpawns(String text) {im.setField("Spawns", text);}
public void setDecomposesTo(String text) {im.setField("Decomposes To", text);}
public void setIsRelatedTo(String text) {im.setField("Is Related To", text);}
public void setStakeholders(String text) {im.setField("Stakeholders", text);}
public void setTestEnvironment(String text) {im.setField("Test Environment", text);}
public void setTestObjectives(String text) {im.setField("Test Objectives", text);}
public void setBuildID(String text) {im.setField("Build ID", text);}
public void setTestObjectivePassCount(String text) {im.setField("Test Objective Pass Count", text);}
public void setTestObjectiveFailCount(String text) {im.setField("Test Objective Fail Count", text);}
public void setTestObjectiveOtherCount(String text) {im.setField("Test Objective Other Count", text);}
public void setTestObjectiveRunCount(String text) {im.setField("Test Objective Run Count", text);}
public void setDetectedDuring(String text) {im.setField("Detected During", text);}
public void setOpenDefectCount(String text) {im.setField("Open Defect Count", text);}
public void setClosedDefectCount(String text) {im.setField("Closed Defect Count", text);}
public void setTotalDefectCount(String text) {im.setField("Total Defect Count", text);}
public void setSessionType(String text) {im.setField("Session Type", text);}
public void setTotalPassCount(String text) {im.setField("Total Pass Count", text);}
public void setTotalFailCount(String text) {im.setField("Total Fail Count", text);}
public void setTotalOtherCount(String text) {im.setField("Total Other Count", text);}
public void setTotalRunCount(String text) {im.setField("Total Run Count", text);}
public void setProduct(String text) {im.setField("Product", text);}
public void setTotalPlannedCount(String text) {im.setField("Total Planned Count", text);}
public void setPlannedStartDate(String text) {im.setField("Planned Start Date", text);}
public void setPlannedEndDate(String text) {im.setField("Planned End Date", text);}
public void setFoundbyTestObjective(String text) {im.setField("Found by Test Objective", text);}
public void setTestTool(String text) {im.setField("Test Tool", text);}
public void setSharedAutomatedBehaviorOnError(String text) {im.setField("Shared Automated Behavior On Error", text);}
public void setPlannedStartTime(String text) {im.setField("Planned Start Time", text);}
public void setActualStartTime(String text) {im.setField("Actual Start Time", text);}
public void setSharedExternalID(String text) {im.setField("Shared External ID", text);}
public void setSharedExternalScriptName(String text) {im.setField("Shared External Script Name", text);}
public void setRequiresValidation(String text) {im.setField("Requires Validation", text);}
public void setActualBudget(String text) {im.setField("Actual Budget", text);}
public void setEstimatedBudget(String text) {im.setField("Estimated Budget", text);}
public void setTotalActualBudget(String text) {im.setField("Total Actual Budget", text);}
public void setBudgetVariance(String text) {im.setField("Budget Variance", text);}
public void setActualEffort(String text) {im.setField("Actual Effort", text);}
public void setTotalEstimatedBudget(String text) {im.setField("Total Estimated Budget", text);}
public void setTotalActualEffort(String text) {im.setField("Total Actual Effort", text);}
public void setTestPlans(String text) {im.setField("Test Plans", text);}
public void setTotalEstimatedEffort(String text) {im.setField("Total Estimated Effort", text);}
public void setHealthValue(String text) {im.setField("Health Value", text);}
public void setTotalEffortVariance(String text) {im.setField("Total Effort Variance", text);}
public void setEffortVariance(String text) {im.setField("Effort Variance", text);}
public void setEstimatedEffort(String text) {im.setField("Estimated Effort", text);}
public void setProcessCategory(String text) {im.setField("Process Category", text);}
public void setOpenChangePackageCount(String text) {im.setField("Open Change Package Count", text);}
public void setChangePackageCount(String text) {im.setField("Change Package Count", text);}
public void setTotalChangePackageCount(String text) {im.setField("Total Change Package Count", text);}
public void setTotalOpenChangePackageCount(String text) {im.setField("Total Open Change Package Count", text);}
public void setManagedType(String text) {im.setField("Managed Type", text);}
public void setChangeRequests(String text) {im.setField("Change Requests", text);}
public void setDefects(String text) {im.setField("Defects", text);}
public void setOpenTaskCount(String text) {im.setField("Open Task Count", text);}
public void setTotalTaskCount(String text) {im.setField("Total Task Count", text);}
public void setPendingTaskCount(String text) {im.setField("Pending Task Count", text);}
public void setClosedTaskCount(String text) {im.setField("Closed Task Count", text);}
public void setTotalRemainingEffort(String text) {im.setField("Total Remaining Effort", text);}
public void setChangePackageEntryCount(String text) {im.setField("Change Package Entry Count", text);}
public void setDaysinCurrentState(String text) {im.setField("Days in Current State", text);}
public void setAverageBudgetVariance(String text) {im.setField("Average Budget Variance", text);}
public void setAverageEffortVariance(String text) {im.setField("Average Effort Variance", text);}
public void setProductHealthValue(String text) {im.setField("Product Health Value", text);}
public void setGreenProjectCount(String text) {im.setField("Green Project Count", text);}
public void setYellowProjectCount(String text) {im.setField("Yellow Project Count", text);}
public void setRedProjectCount(String text) {im.setField("Red Project Count", text);}
public void setActiveProjectCount(String text) {im.setField("Active Project Count", text);}
public void setProposedProjectCount(String text) {im.setField("Proposed Project Count", text);}
public void setInactiveProjectCount(String text) {im.setField("Inactive Project Count", text);}
public void setCompletedProjectCount(String text) {im.setField("Completed Project Count", text);}
public void setSourceProject(String text) {im.setField("Source Project", text);}
public void setLinesofcode(String text) {im.setField("Lines of code", text);}
public void setSourceFileCount(String text) {im.setField("Source File Count", text);}
public void setSourceSubprojectCount(String text) {im.setField("Source Subproject Count", text);}
public void setSourceCheckpointCount(String text) {im.setField("Source Checkpoint Count", text);}
public void setOpenChangeRequestCountforProject(String text) {im.setField("Open Change Request Count for Project", text);}
public void setPendingDefectCount(String text) {im.setField("Pending Defect Count", text);}
public void setPendingTestObjectiveCount(String text) {im.setField("Pending Test Objective Count", text);}
public void setOpenTestObjectiveCount(String text) {im.setField("Open Test Objective Count", text);}
public void setClosedTestObjectiveCount(String text) {im.setField("Closed Test Objective Count", text);}
public void setTotalTestObjectiveCount(String text) {im.setField("Total Test Objective Count", text);}
public void setTotalPassPercentageofPlanned(String text) {im.setField("Total Pass Percentage of Planned", text);}
public void setTotalRunPercentage(String text) {im.setField("Total Run Percentage", text);}
public void setTotalBudgetVariance(String text) {im.setField("Total Budget Variance", text);}
public void setProjectStartOriginalEstimate(String text) {im.setField("Project Start Original Estimate", text);}
public void setProjectAcceptanceOriginalEstimate(String text) {im.setField("Project Acceptance Original Estimate", text);}
public void setProjectPlanOriginalEstimate(String text) {im.setField("Project Plan Original Estimate", text);}
public void setFunctionalSpecificationFreezeOriginalEstimate(String text) {im.setField("Functional Specification Freeze Original Estimate", text);}
public void setCodeFreezeOriginalEstimate(String text) {im.setField("Code Freeze Original Estimate", text);}
public void setProjectStartCurrentEstimate(String text) {im.setField("Project Start Current Estimate", text);}
public void setProjectAcceptanceCurrentEstimate(String text) {im.setField("Project Acceptance Current Estimate", text);}
public void setProjectPlanCurrentEstimate(String text) {im.setField("Project Plan Current Estimate", text);}
public void setFunctionalSpecificationFreezeCurrentEstimate(String text) {im.setField("Functional Specification Freeze Current Estimate", text);}
public void setCodeFreezeCurrentEstimate(String text) {im.setField("Code Freeze Current Estimate", text);}
public void setProjectStartActual(String text) {im.setField("Project Start Actual", text);}
public void setProjectAcceptanceActual(String text) {im.setField("Project Acceptance Actual", text);}
public void setProjectPlanActual(String text) {im.setField("Project Plan Actual", text);}
public void setFunctionalSpecificationFreezeActual(String text) {im.setField("Functional Specification Freeze Actual", text);}
public void setCodeFreezeActual(String text) {im.setField("Code Freeze Actual", text);}
public void setDevelopmentCompleteOriginalEstimate(String text) {im.setField("Development Complete Original Estimate", text);}
public void setDevelopmentCompleteCurrentEstimate(String text) {im.setField("Development Complete Current Estimate", text);}
public void setDevelopmentCompleteActual(String text) {im.setField("Development Complete Actual", text);}
public void setArtifactAffected(String text) {im.setField("Artifact Affected", text);}
public void setDayCount(String text) {im.setField("Day Count", text);}
public void setBurnRate(String text) {im.setField("Burn Rate", text);}
public void setRunningCost(String text) {im.setField("Running Cost", text);}
public void setCostStabilityIndex(String text) {im.setField("Cost Stability Index", text);}
public void setOriginalSchedule(String text) {im.setField("Original Schedule", text);}
public void setCurrentSchedule(String text) {im.setField("Current Schedule", text);}
public void setScheduleStabilityIndex(String text) {im.setField("Schedule Stability Index", text);}
public void setInitialBaseline(String text) {im.setField("Initial Baseline", text);}
public void setRequirementStabilityIndex(String text) {im.setField("Requirement Stability Index", text);}
public void setRequirementDefectsSlipped(String text) {im.setField("Requirement Defects Slipped", text);}
public void setCodingDefectsSlipped(String text) {im.setField("Coding Defects Slipped", text);}
public void setDesignDefectsSlipped(String text) {im.setField("Design Defects Slipped", text);}
public void setOpenTestSessionCount(String text) {im.setField("Open Test Session Count", text);}
public void setPendingTestSessionCount(String text) {im.setField("Pending Test Session Count", text);}
public void setClosedTestSessionCount(String text) {im.setField("Closed Test Session Count", text);}
public void setTotalPassPercentageofRun(String text) {im.setField("Total Pass Percentage of Run", text);}
public void setTotalChangePackageEntryCount(String text) {im.setField("Total Change Package Entry Count", text);}
public void setRequirementCountChange(String text) {im.setField("Requirement Count Change", text);}
public void setDocumentChurnfromInitialBaseline(String text) {im.setField("Document Churn from Initial Baseline", text);}
public void setTestsAsOfDateSet(String text) {im.setField("Tests As Of Date Set", text);}
public void setTestObjectivePlannedCount(String text) {im.setField("Test Objective Planned Count", text);}
public void setTestsAsOfDateCount(String text) {im.setField("Tests As Of Date Count", text);}
public void setSuspectResults(String text) {im.setField("Suspect Results", text);}
public void setBlocks(String text) {im.setField("Blocks", text);}
public void setBlocked(String text) {im.setField("Blocked", text);}
public void setTestObjectiveBlockedCount(String text) {im.setField("Test Objective Blocked Count", text);}
public void setTotalBlockedCount(String text) {im.setField("Total Blocked Count", text);}
public void setTestObjectiveSuspectResultsCount(String text) {im.setField("Test Objective Suspect Results Count", text);}
public void setTotalSuspectResultsCount(String text) {im.setField("Total Suspect Results Count", text);}
public void setModifiedCountSinceInitialBaseline(String text) {im.setField("Modified Count Since Initial Baseline", text);}
public void setIncludeSubsegmentinMetrics(String text) {im.setField("Include Subsegment in Metrics", text);}
public void setSolutionGlobalUnallocatedChangeRequestCount(String text) {im.setField("Solution Global Unallocated Change Request Count", text);}
public void setSolutionGlobalPartiallySatisfiedChangeRequestCount(String text) {im.setField("Solution Global Partially Satisfied Change Request Count", text);}
public void setValidatedByTraceCount(String text) {im.setField("Validated By Trace Count", text);}
public void setRequirementCount(String text) {im.setField("Requirement Count", text);}
public void setSpecificationCount(String text) {im.setField("Specification Count", text);}
public void setTestCaseCount(String text) {im.setField("Test Case Count", text);}
public void setTestSuiteCount(String text) {im.setField("Test Suite Count", text);}
public void setIncludedDocumentCountinProject(String text) {im.setField("Included Document Count in Project", text);}
public void setInsertedDocumentCountinProject(String text) {im.setField("Inserted Document Count in Project", text);}
public void setRequirementCountfromInitialBaseline(String text) {im.setField("Requirement Count from Initial Baseline", text);}
public void setSuspectContentPercentageInProject(String text) {im.setField("Suspect Content Percentage In Project", text);}
public void setUntracedContentPercentage(String text) {im.setField("Untraced Content Percentage", text);}
public void setUnsatisfiedRequirementsPercentage(String text) {im.setField("Unsatisfied Requirements Percentage", text);}
public void setUnverifiedRequirementsPercentage(String text) {im.setField("Unverified Requirements Percentage", text);}
public void setUnvalidatedRequirementsPercentage(String text) {im.setField("Unvalidated Requirements Percentage", text);}
public void setUntracedInputsPercentage(String text) {im.setField("Untraced Inputs Percentage", text);}
public void setAllContentCount(String text) {im.setField("All Content Count", text);}
public void setContentBackTraceCount(String text) {im.setField("Content Back Trace Count", text);}
public void setContentWithoutBackTracesCount(String text) {im.setField("Content Without Back Traces Count", text);}
public void setMeaningfulContentCount(String text) {im.setField("Meaningful Content Count", text);}
public void setSuspectContentCount(String text) {im.setField("Suspect Content Count", text);}
public void setSuspectRelationshipCount(String text) {im.setField("Suspect Relationship Count", text);}
public void setIncludedDocumentCount(String text) {im.setField("Included Document Count", text);}
public void setInsertedDocumentCount(String text) {im.setField("Inserted Document Count", text);}
public void setOpenChangeRequestCount(String text) {im.setField("Open Change Request Count", text);}
public void setClosedChangeRequestCount(String text) {im.setField("Closed Change Request Count", text);}
public void setContentWithoutBackTracesPercentage(String text) {im.setField("Content Without Back Traces Percentage", text);}
public void setSuspectContentPercentage(String text) {im.setField("Suspect Content Percentage", text);}
public void setExploratoryTestLog(String text) {im.setField("Exploratory Test Log", text);}
public void setClassification(String text) {im.setField("Classification", text);}
public void setYesterdayDocumentChurn(String text) {im.setField("Yesterday Document Churn", text);}
public void setLastWeekDocumentChurn(String text) {im.setField("Last Week Document Churn", text);}
public void setTraceStatus(String text) {im.setField("Trace Status", text);}
public void setSharedExpectedResults(String text) {im.setField("Shared Expected Results", text);}
public void setDownstreamTraceCount(String text) {im.setField("Downstream Trace Count", text);}
public void setUpstreamTraceCount(String text) {im.setField("Upstream Trace Count", text);}
public void setPostBranchEdit(String text) {im.setField("Post Branch Edit", text);}
public void setAuthorizedChangeCount(String text) {im.setField("Authorized Change Count", text);}
public void setTotalWorkItemCount(String text) {im.setField("Total Work Item Count", text);}
public void setPendingWorkItemCount(String text) {im.setField("Pending Work Item Count", text);}
public void setOpenWorkItemCount(String text) {im.setField("Open Work Item Count", text);}
public void setClosedWorkItemCount(String text) {im.setField("Closed Work Item Count", text);}
public void setValidatedByPassCount(String text) {im.setField("Validated By Pass Count", text);}
public void setValidatedByPassPercentage(String text) {im.setField("Validated By Pass Percentage", text);}
public void setTotalTestSessionCount(String text) {im.setField("Total Test Session Count", text);}
public void setOpenAndPendingDefectCount(String text) {im.setField("Open And Pending Defect Count", text);}
public void setTotalContentPassCount(String text) {im.setField("Total Content Pass Count", text);}
public void setTotalContentFailCount(String text) {im.setField("Total Content Fail Count", text);}
public void setTotalContentOtherCount(String text) {im.setField("Total Content Other Count", text);}
public void setTotalContentRunCount(String text) {im.setField("Total Content Run Count", text);}
public void setContentFailCount(String text) {im.setField("Content Fail Count", text);}
public void setContentPassCount(String text) {im.setField("Content Pass Count", text);}
public void setContentOtherCount(String text) {im.setField("Content Other Count", text);}
public void setContentRunCount(String text) {im.setField("Content Run Count", text);}
public void setRecordResultsAsShared(String text) {im.setField("Record Results As Shared", text);}
public void setAdditionalComments(String text) {im.setField("Additional Comments", text);}
public void setCategoryIntValue(String text) {im.setField("Category Int Value", text);}
public void setModelledBy(String text) {im.setField("Modelled By", text);}
public void setSharedCause(String text) {im.setField("Shared Cause", text);}
public void setSharedEffect(String text) {im.setField("Shared Effect", text);}
public void setDetection(String text) {im.setField("Detection", text);}
public void setMitigatedBy(String text) {im.setField("Mitigated By", text);}
public void setVerifiedBy(String text) {im.setField("Verified By", text);}
public void setVerifiedByPassCount(String text) {im.setField("Verified By Pass Count", text);}
public void setVerifiedByTraceCount(String text) {im.setField("Verified By Trace Count", text);}
public void setVerifiedByPassPercentage(String text) {im.setField("Verified By Pass Percentage", text);}
public void setHide(String text) {im.setField("Hide", text);}
public void setSatisfies(String text) {im.setField("Satisfies", text);}
public void setChangesAuthorizedBy(String text) {im.setField("Changes Authorized By", text);}
public void setValidates(String text) {im.setField("Validates", text);}
public void setDocuments(String text) {im.setField("Documents", text);}
public void setSpawnedBy(String text) {im.setField("Spawned By", text);}
public void setDecomposedFrom(String text) {im.setField("Decomposed From", text);}
public void setIsRelatedToBW(String text) {im.setField("Is Related To'", text);}
public void setPassCount(String text) {im.setField("Pass Count", text);}
public void setTestObjectiveFor(String text) {im.setField("Test Objective For", text);}
public void setTestPlanFor(String text) {im.setField("Test Plan For", text);}
public void setChangeRequestFor(String text) {im.setField("Change Request For", text);}
public void setDefectFor(String text) {im.setField("Defect For", text);}
public void setBlockedBy(String text) {im.setField("Blocked By", text);}
public void setModels(String text) {im.setField("Models", text);}
public void setMitigates(String text) {im.setField("Mitigates", text);}
public void setVerifies(String text) {im.setField("Verifies", text);}
public void setRevisionDate(String text) {im.setField("Revision Date", text);}
public void setDateCompleted(String text) {im.setField("Date Completed", text);}
public void setValidChangeRequest(String text) {im.setField("Valid Change Request", text);}
public void setAllowEdits(String text) {im.setField("Allow Edits", text);}
public void setUseHierarchicalEditability(String text) {im.setField("Use Hierarchical Editability", text);}
public void setAllowTraces(String text) {im.setField("Allow Traces", text);}
public void setAllowLinks(String text) {im.setField("Allow Links", text);}
public void setTaskPhase(String text) {im.setField("Task Phase", text);}
public void setDocumentPhase(String text) {im.setField("Document Phase", text);}
public void setRunCount(String text) {im.setField("Run Count", text);}
public void setFailCount(String text) {im.setField("Fail Count", text);}
public void setOtherCount(String text) {im.setField("Other Count", text);}
public void setLastResult(String text) {im.setField("Last Result", text);}
public void setValidTestStepChangeRequest(String text) {im.setField("Valid Test Step Change Request", text);}
public void setTestStepEditable(String text) {im.setField("Test Step Editable", text);}
public void setEditableValue(String text) {im.setField("Editable Value", text);}
public void setEditable(String text) {im.setField("Editable", text);}
public void setCompletedTestSessions(String text) {im.setField("Completed Test Sessions", text);}
public void setTestSessionPhase(String text) {im.setField("Test Session Phase", text);}
public void setTestObjectivePhase(String text) {im.setField("Test Objective Phase", text);}
public void setActiveTestSessions(String text) {im.setField("Active Test Sessions", text);}
public void setProjectPhase(String text) {im.setField("Project Phase", text);}
public void setTotalBudgetVarianceRange(String text) {im.setField("Total Budget Variance Range", text);}
public void setTotalEffortVarianceRange(String text) {im.setField("Total Effort Variance Range", text);}
public void setHealthValueRange(String text) {im.setField("Health Value Range", text);}
public void setProductHealthValueRange(String text) {im.setField("Product Health Value Range", text);}
public void setActiveProjects(String text) {im.setField("Active Projects", text);}
public void setProposedProjects(String text) {im.setField("Proposed Projects", text);}
public void setCompletedProjects(String text) {im.setField("Completed Projects", text);}
public void setInactiveProjects(String text) {im.setField("Inactive Projects", text);}
public void setAtRiskProjects(String text) {im.setField("At Risk Projects", text);}
public void setManagedProductCount(String text) {im.setField("Managed Product Count", text);}
public void setBelongstoPortfolio(String text) {im.setField("Belongs to Portfolio", text);}
public void setBelongstoProduct(String text) {im.setField("Belongs to Product", text);}
public void setTestSessionTestsCount(String text) {im.setField("Test Session Tests Count", text);}
public void setBlocking(String text) {im.setField("Blocking", text);}
public void setPlannedCount(String text) {im.setField("Planned Count", text);}
public void setValidatedByTraceHealth(String text) {im.setField("Validated By Trace Health", text);}
public void setRetiredStates(String text) {im.setField("Retired States", text);}
public void setTestStepTestCasesCount(String text) {im.setField("Test Step Test Cases Count", text);}
public void setTestCaseEditable(String text) {im.setField("Test Case Editable", text);}
public void setRunPercentage(String text) {im.setField("Run Percentage", text);}
public void setLastVerdictType(String text) {im.setField("Last Verdict Type", text);}
public void setisParent(String text) {im.setField("isParent", text);}
public void setRPN(String text) {im.setField("RPN", text);}
public void setVerifiedByTraceHealth(String text) {im.setField("Verified By Trace Health", text);}
public void setRelatedProject(String text) {im.setField("Related Project", text);}
public void setInputSharedItemRevisionDate(String text) {im.setField("Input Shared Item Revision Date", text);}
public void setTextAttachments(String text) {im.setField("Text Attachments", text);}
public void setProjectState(String text) {im.setField("Project State", text);}
public void setProjectAllowScopeExpansion(String text) {im.setField("Project Allow Scope Expansion", text);}
public void setParameterValuesFromSharedItem(String text) {im.setField("Parameter Values From Shared Item", text);}
public void setParametersFromSharedItem(String text) {im.setField("Parameters From Shared Item", text);}
public void setProductParameters(String text) {im.setField("Product Parameters", text);}
public void setProjectRequiresChangeRequests(String text) {im.setField("Project Requires Change Requests", text);}
public void setExternalID(String text) {im.setField("External ID", text);}
public void setExternalScriptName(String text) {im.setField("External Script Name", text);}
public void setAutomatedBehaviorOnError(String text) {im.setField("Automated Behavior On Error", text);}
public void setProjectPhaseFVA(String text) {im.setField("Project Phase FVA", text);}
public void setProductManager(String text) {im.setField("Product Manager", text);}
public void setProjectClassification(String text) {im.setField("Project Classification", text);}
public void setExpectedResults(String text) {im.setField("Expected Results", text);}
public void setCause(String text) {im.setField("Cause", text);}
public void setEffect(String text) {im.setField("Effect", text);}
public void setImplementedBy(String text) {im.setField("Implemented By", text);}
public void setImplements(String text) {im.setField("Implements", text);}
public void setRiskControlOption(String text) {im.setField("Risk Control Option", text);}
public void setSafetyClassification(String text) {im.setField("Safety Classification", text);}
public void setCausedBy(String text) {im.setField("Caused By", text);}
public void setCauses(String text) {im.setField("Causes", text);}
public void setSharedHazardousSituation(String text) {im.setField("Shared Hazardous Situation", text);}
public void setSharedHarm(String text) {im.setField("Shared Harm", text);}
public void setSharedForeseeablesequenceofevents(String text) {im.setField("Shared Foreseeable sequence of events", text);}
public void setSeverity(String text) {im.setField("Severity", text);}
public void setOccurrence(String text) {im.setField("Occurrence", text);}
public void setRiskIndex(String text) {im.setField("Risk Index", text);}
public void setRiskLevel(String text) {im.setField("Risk Level", text);}
public void setHazardousSituation(String text) {im.setField("Hazardous Situation", text);}
public void setForeseeablesequenceofevents(String text) {im.setField("Foreseeable sequence of events", text);}
public void setHarm(String text) {im.setField("Harm", text);}
public void setS1O1Count(String text) {im.setField("S1O1 Count", text);}
public void setS1O2Count(String text) {im.setField("S1O2 Count", text);}
public void setS1O3Count(String text) {im.setField("S1O3 Count", text);}
public void setS1O4Count(String text) {im.setField("S1O4 Count", text);}
public void setS1O5Count(String text) {im.setField("S1O5 Count", text);}
public void setS2O1Count(String text) {im.setField("S2O1 Count", text);}
public void setS2O2Count(String text) {im.setField("S2O2 Count", text);}
public void setS2O3Count(String text) {im.setField("S2O3 Count", text);}
public void setS2O4Count(String text) {im.setField("S2O4 Count", text);}
public void setS2O5Count(String text) {im.setField("S2O5 Count", text);}
public void setS3O1Count(String text) {im.setField("S3O1 Count", text);}
public void setS3O2Count(String text) {im.setField("S3O2 Count", text);}
public void setS3O3Count(String text) {im.setField("S3O3 Count", text);}
public void setS3O4Count(String text) {im.setField("S3O4 Count", text);}
public void setS3O5Count(String text) {im.setField("S3O5 Count", text);}
public void setS4O1Count(String text) {im.setField("S4O1 Count", text);}
public void setS4O2Count(String text) {im.setField("S4O2 Count", text);}
public void setS4O3Count(String text) {im.setField("S4O3 Count", text);}
public void setS4O4Count(String text) {im.setField("S4O4 Count", text);}
public void setS4O5Count(String text) {im.setField("S4O5 Count", text);}
public void setS5O1Count(String text) {im.setField("S5O1 Count", text);}
public void setS5O2Count(String text) {im.setField("S5O2 Count", text);}
public void setS5O3Count(String text) {im.setField("S5O3 Count", text);}
public void setS5O4Count(String text) {im.setField("S5O4 Count", text);}
public void setS5O5Count(String text) {im.setField("S5O5 Count", text);}
public void setTypeIntValue(String text) {im.setField("Type Int Value", text);}
public void setApproversRequired(String text) {im.setField("Approvers Required", text);}
public void setApprovedBy(String text) {im.setField("Approved By", text);}
public void setMostRecentlyApprovedDate(String text) {im.setField("Most Recently Approved Date", text);}
public void setApprovedCommentsLog(String text) {im.setField("Approved Comments Log", text);}
public void setApprovalMatrix(String text) {im.setField("Approval Matrix", text);}
public void setBusinessObjectives(String text) {im.setField("Business Objectives", text);}
public void setPlannedReleaseEndDate(String text) {im.setField("Planned Release End Date", text);}
public void setScrumMaster(String text) {im.setField("Scrum Master", text);}
public void setSprintStartDate(String text) {im.setField("Sprint Start Date", text);}
public void setSprintEndDate(String text) {im.setField("Sprint End Date", text);}
public void setSprintDuration(String text) {im.setField("Sprint Duration", text);}
public void setOpenUserStories(String text) {im.setField("Open User Stories", text);}
public void setCompletedUserStories(String text) {im.setField("Completed User Stories", text);}
public void setTotalTimeSpentonSprinttoDate(String text) {im.setField("Total Time Spent on Sprint to Date", text);}
public void setDaysRemaininginSprint(String text) {im.setField("Days Remaining in Sprint", text);}
public void setLessonsLearned(String text) {im.setField("Lessons Learned", text);}
public void setSprintSuccesses(String text) {im.setField("Sprint Successes", text);}
public void setSprintReviewNotes(String text) {im.setField("Sprint Review Notes", text);}
public void setReleases(String text) {im.setField("Releases", text);}
public void setSprintPlanningNotes(String text) {im.setField("Sprint Planning Notes", text);}
public void setStoryOwner(String text) {im.setField("Story Owner", text);}
public void setRelativePriority(String text) {im.setField("Relative Priority", text);}
public void setStoryElaborationNotes(String text) {im.setField("Story Elaboration Notes", text);}
public void setHowtoDemo(String text) {im.setField("How to Demo", text);}
public void setSprintImpedimentNotes(String text) {im.setField("Sprint Impediment Notes", text);}
public void setTasks(String text) {im.setField("Tasks", text);}
public void setTaskFor(String text) {im.setField("Task For", text);}
public void setStartDate(String text) {im.setField("Start Date", text);}
public void setPlannedNumberofSprints(String text) {im.setField("Planned Number of Sprints", text);}
public void setReleaseBacklogCount(String text) {im.setField("Release Backlog Count", text);}
public void setUserStories(String text) {im.setField("User Stories", text);}
public void setImplementedIn(String text) {im.setField("Implemented In", text);}
public void setEstimatedEffortforSprint(String text) {im.setField("Estimated Effort for Sprint", text);}
public void setTimeSpentonStory(String text) {im.setField("Time Spent on Story", text);}
public void setElapsedDaysSinceCreateDate(String text) {im.setField("Elapsed Days Since Create Date", text);}
public void setTotalTimeloggedagainstSprint(String text) {im.setField("Total Time logged against Sprint", text);}
public void setStorySize(String text) {im.setField("Story Size", text);}
public void setRemainingEffort(String text) {im.setField("Remaining Effort", text);}
public void setTotalTimeSpentonTasktoDate(String text) {im.setField("Total Time Spent on Task to Date", text);}
public void setRemainingEffortforStory(String text) {im.setField("Remaining Effort for Story", text);}
public void setRemainingEffortforSprint(String text) {im.setField("Remaining Effort for Sprint", text);}
public void setSprintGoalsandObjectives(String text) {im.setField("Sprint Goals and Objectives", text);}
public void setAverageSprintVelocity(String text) {im.setField("Average Sprint Velocity", text);}
public void setIdealSprintDuration(String text) {im.setField("Ideal Sprint Duration", text);}
public void setSprintReviewAttachments(String text) {im.setField("Sprint Review Attachments", text);}
public void setSprintRetrospectiveAttachments(String text) {im.setField("Sprint Retrospective Attachments", text);}
public void setImpedimentDetails(String text) {im.setField("Impediment Details", text);}
public void setImpedimentResolution(String text) {im.setField("Impediment Resolution", text);}
public void setImpedimentWorkaround(String text) {im.setField("Impediment Workaround", text);}
public void setTargetResolutionDate(String text) {im.setField("Target Resolution Date", text);}
public void setImpediments(String text) {im.setField("Impediments", text);}
public void setAffectedItem(String text) {im.setField("Affected Item", text);}
public void setDaystoResolution(String text) {im.setField("Days to Resolution", text);}
public void setAffectedTask(String text) {im.setField("Affected Task", text);}
public void setAffectedBy(String text) {im.setField("Affected By", text);}
public void setStoryCategory(String text) {im.setField("Story Category", text);}
public void setSubStory(String text) {im.setField("Sub Story", text);}
public void setEpic(String text) {im.setField("Epic", text);}
public void setEpicStorySize(String text) {im.setField("Epic Story Size", text);}
public void setTotalVelocity(String text) {im.setField("Total Velocity", text);}
public void setReleaseTotalStoryPoints(String text) {im.setField("Release Total Story Points", text);}
public void setSprintTeam(String text) {im.setField("Sprint Team", text);}
public void setTeamMembers(String text) {im.setField("Team Members", text);}
public void setCompletedSprints(String text) {im.setField("Completed Sprints", text);}
public void setAverageTeamVelocity(String text) {im.setField("Average Team Velocity", text);}
public void setMaximumTeamVelocity(String text) {im.setField("Maximum Team Velocity", text);}
public void setMinimumTeamVelocity(String text) {im.setField("Minimum Team Velocity", text);}
public void setSprintScrumMaster(String text) {im.setField("Sprint Scrum Master", text);}
public void setSprintTeamMembers(String text) {im.setField("Sprint Team Members", text);}
public void setReleases2(String text) {im.setField("Releases 2", text);}
public void setProductBacklog(String text) {im.setField("Product Backlog", text);}
public void setProductOwner(String text) {im.setField("Product Owner", text);}
public void setCurrentSprint(String text) {im.setField("Current Sprint", text);}
public void setRank(String text) {im.setField("Rank", text);}
public void setEstEffortInDays(String text) {im.setField("Est Effort In Days", text);}
public void setProposedText(String text) {im.setField("Proposed Text", text);}
public void setSynchedWithMaster(String text) {im.setField("Synched With Master", text);}
public void setCopyFromMaster(String text) {im.setField("Copy From Master", text);}
public void setDifferences(String text) {im.setField("Differences", text);}
public void setManageChanges(String text) {im.setField("Manage Changes", text);}
public void setAllowNodeEdits(String text) {im.setField("Allow Node Edits", text);}
public void setProposedPriority(String text) {im.setField("Proposed Priority", text);}
public void setOpenChangeRequestID(String text) {im.setField("Open Change Request ID", text);}
public void setChangeStatus(String text) {im.setField("Change Status", text);}
public void setBaselineDocument(String text) {im.setField("Baseline Document", text);}
public void setSharedModelChangedDate(String text) {im.setField("Shared Model Changed Date", text);}
public void setSharedModelDescription(String text) {im.setField("Shared Model Description", text);}
public void setSharedModelLink(String text) {im.setField("Shared Model Link", text);}
public void setSharedModelReference(String text) {im.setField("Shared Model Reference", text);}
public void setSharedModelType(String text) {im.setField("Shared Model Type", text);}
public void setisModel(String text) {im.setField("isModel", text);}
public void setModelChangedDate(String text) {im.setField("Model Changed Date", text);}
public void setModelDescription(String text) {im.setField("Model Description", text);}
public void setModelLink(String text) {im.setField("Model Link", text);}
public void setModelReference(String text) {im.setField("Model Reference", text);}
public void setModelType(String text) {im.setField("Model Type", text);}
public void setModelFile(String text) {im.setField("Model File", text);}
public void setModellingTool(String text) {im.setField("Modelling Tool", text);}
public void setModelLocked(String text) {im.setField("Model Locked", text);}
public void setModelSynchronizeDate(String text) {im.setField("Model Synchronize Date", text);}
public void setModelSynchronizeStatus(String text) {im.setField("Model Synchronize Status", text);}
public void setModelSynchronizeUser(String text) {im.setField("Model Synchronize User", text);}
public void setModelSynchronizeVersion(String text) {im.setField("Model Synchronize Version", text);}
public void setAllowModelOwnedFieldEdits(String text) {im.setField("Allow Model Owned Field Edits", text);}
public void setSharedName(String text) {im.setField("Shared Name", text);}
public void setSimulatedModel(String text) {im.setField("Simulated Model", text);}
public void setLastBuildStatus(String text) {im.setField("Last Build Status", text);}
public void setIntegrationDetails(String text) {im.setField("Integration Details", text);}
public void setIntegrationStatus(String text) {im.setField("Integration Status", text);}
public void setLocationInWindchill(String text) {im.setField("Location In Windchill", text);}
public void setSelectWindchilllLocation(String text) {im.setField("Select Windchilll Location", text);}
public void setSendtoWindchill(String text) {im.setField("Send to Windchill", text);}
public void setWindchillItem(String text) {im.setField("Windchill Item", text);}
public void setWindchillLocation(String text) {im.setField("Windchill Location", text);}
public void setWindchillPushProgress(String text) {im.setField("Windchill Push Progress", text);}
public void setWindchillHide(String text) {im.setField("Windchill Hide", text);}
public void setxxAffectedVersion(String text) {im.setField("xx Affected Version", text);}
public void setProductReleases(String text) {im.setField("Product Releases", text);}
public void setRelatedProduct(String text) {im.setField("Related Product", text);}
public void setTeam(String text) {im.setField("Team", text);}
public void setTeamSprints(String text) {im.setField("Team Sprints", text);}
public void setSubscribedUsersList(String text) {im.setField("Subscribed Users List", text);}
public void setAmISubscribed(String text) {im.setField("Am I Subscribed", text);}
public void setNotSubscribed(String text) {im.setField("Not Subscribed", text);}
public void setSubscribeToChanges(String text) {im.setField("Subscribe To Changes", text);}
public void setPendingDevelopmentActivityforProduct(String text) {im.setField("Pending Development Activity for Product", text);}
public void setOpenDevelopmentActivityforProduct(String text) {im.setField("Open Development Activity for Product", text);}
public void setClosedDevelopmentActivityforProduct(String text) {im.setField("Closed Development Activity for Product", text);}
public void setTotalDevelopmentActivityCountforProduct(String text) {im.setField("Total Development Activity Count for Product", text);}
public void setPendingTestActivityforProduct(String text) {im.setField("Pending Test Activity for Product", text);}
public void setOpenTestActivityforProduct(String text) {im.setField("Open Test Activity for Product", text);}
public void setClosedTestActivityforProduct(String text) {im.setField("Closed Test Activity for Product", text);}
public void setTotalTestActivityforProduct(String text) {im.setField("Total Test Activity for Product", text);}
public void setProjectChangeRequests(String text) {im.setField("Project Change Requests", text);}
public void setProjectDefects(String text) {im.setField("Project Defects", text);}
public void setTestObjectiveDefects(String text) {im.setField("Test Objective Defects", text);}
public void setTotalOpenDays(String text) {im.setField("Total Open Days", text);}
public void setTestSessions(String text) {im.setField("Test Sessions", text);}
public void setTask(String text) {im.setField("Task", text);}
public void setBuildLog(String text) {im.setField("Build Log", text);}
public void setProjectApprovers(String text) {im.setField("Project Approvers", text);}
public void setTeamVelocity(String text) {im.setField("Team Velocity", text);}
public void setSprintCapacity(String text) {im.setField("Sprint Capacity", text);}
public void setSprintCapacityHealth(String text) {im.setField("Sprint Capacity Health", text);}
public void setOriginalProject(String text) {im.setField("Original Project", text);}
public void setSequelProjects(String text) {im.setField("Sequel Projects", text);}
public void setDOORSID(String text) {im.setField("DOORSID", text);}
public void setDoorsLink(String text) {im.setField("Doors Link", text);}
public void setResponsible(String text) {im.setField("Responsible", text);}
public void setAccountable(String text) {im.setField("Accountable", text);}
public void setConsulted(String text) {im.setField("Consulted", text);}
public void setInformed(String text) {im.setField("Informed", text);}
public void setFunctionalAllocation(String text) {im.setField("Functional Allocation", text);}
public void setFunctionalRequirements(String text) {im.setField("Functional Requirements", text);}
public void setStructuralAllocation(String text) {im.setField("Structural Allocation", text);}
public void setStructuralRequirements(String text) {im.setField("Structural Requirements", text);}
public void setFunctiontoStructuralAllocation(String text) {im.setField("Function to Structural Allocation", text);}
public void setAllocatedFunctions(String text) {im.setField("Allocated Functions", text);}
public void setArchitectureDocuments(String text) {im.setField("Architecture Documents", text);}
public void setArchitectureDocumentFor(String text) {im.setField("Architecture Document For", text);}
public void setBuilds(String text) {im.setField("Builds", text);}
public void setBuildFor(String text) {im.setField("Build For", text);}
public void setDesigns(String text) {im.setField("Designs", text);}
public void setDesignedBy(String text) {im.setField("Designed By", text);}
public void setFBIs(String text) {im.setField("FBIs", text);}
public void setFBIFor(String text) {im.setField("FBI For", text);}
public void setLibReleases(String text) {im.setField("Lib Releases", text);}
public void setLibReleaseFor(String text) {im.setField("Lib Release For", text);}
public void setSWChangeRequests(String text) {im.setField("SW Change Requests", text);}
public void setSWChangeRequestFor(String text) {im.setField("SW Change Request For", text);}
public void setSpecifies(String text) {im.setField("Specifies", text);}
public void setSpecifiedBy(String text) {im.setField("Specified By", text);}
public void setCRType(String text) {im.setField("CR Type", text);}
public void setMANReqKey(String text) {im.setField("MANReq Key", text);}
public void setComponents(String text) {im.setField("Components", text);}
public void setxxAffectedVersions(String text) {im.setField("xx Affected Versions", text);}
public void setxxTargetVersions(String text) {im.setField("xx Target Versions", text);}
public void setxxAuthor(String text) {im.setField("xx Author", text);}
public void setSolution(String text) {im.setField("Solution", text);}
public void setApprovalDate(String text) {im.setField("Approval Date", text);}
public void setEstimatedEffortEPPE(String text) {im.setField("Estimated Effort EPPE", text);}
public void setEstimatedEffortTier1(String text) {im.setField("Estimated Effort Tier 1", text);}
public void setDiagnostic(String text) {im.setField("Diagnostic", text);}
public void setVariantManagement(String text) {im.setField("Variant Management", text);}
public void setVariantManagementDescription(String text) {im.setField("Variant Management Description", text);}
public void setAffectedFCs(String text) {im.setField("Affected FCs", text);}
public void setSupplier(String text) {im.setField("Supplier", text);}
public void setObjective(String text) {im.setField("Objective", text);}
public void setECU(String text) {im.setField("ECU", text);}
public void setProjektIDinMTBESP(String text) {im.setField("Projekt ID in MTBESP", text);}
public void setProjectActiveDate(String text) {im.setField("Project Active Date", text);}
public void setProjectCompleteDate(String text) {im.setField("Project Complete Date", text);}
public void setMANReqProjectID(String text) {im.setField("MANReq Project ID", text);}
public void setReqIfLink(String text) {im.setField("ReqIf Link", text);}
public void setMANReqHyperlink(String text) {im.setField("MANReq Hyperlink", text);}
public void setDevelopmentType(String text) {im.setField("Development Type", text);}
public void setASIL(String text) {im.setField("ASIL", text);}
public void setEmissionRelevant(String text) {im.setField("Emission Relevant", text);}
public void setDiagnosticRelevant(String text) {im.setField("Diagnostic Relevant", text);}
public void setTestResponsible(String text) {im.setField("Test Responsible", text);}
public void setReviewType(String text) {im.setField("Review Type", text);}
public void setFunctionReviewers(String text) {im.setField("Function Reviewers", text);}
public void setReviewDate(String text) {im.setField("Review Date", text);}
public void setFunctionReviewResults(String text) {im.setField("Function Review Results", text);}
public void setReviewFolder(String text) {im.setField("Review Folder", text);}
public void setCodeTest(String text) {im.setField("Code Test", text);}
public void setBacktoBackTest(String text) {im.setField("Back to Back Test", text);}
public void setSWReview(String text) {im.setField("SW Review", text);}
public void setIntegrationReviewResults(String text) {im.setField("Integration Review Results", text);}
public void setIntegrationReviewers(String text) {im.setField("Integration Reviewers", text);}
public void setIntegrationTest(String text) {im.setField("Integration Test", text);}
public void setFunctionTest(String text) {im.setField("Function Test", text);}
public void setFunctionResponsible(String text) {im.setField("Function Responsible", text);}
public void setFCProjectLink(String text) {im.setField("FC Project Link", text);}
public void setFCModelLink(String text) {im.setField("FC Model Link", text);}
public void setArchitectResponsible(String text) {im.setField("Architect Responsible", text);}
public void setADDContainer(String text) {im.setField("ADD Container", text);}
public void setFunctionalLead(String text) {im.setField("Functional Lead", text);}
public void setDevelopmentEnvironment(String text) {im.setField("Development Environment", text);}
public void setSWCRCount(String text) {im.setField("SW CR Count", text);}
public void setCRCount(String text) {im.setField("CR Count", text);}
public void setOpenSWCRCountForFBI(String text) {im.setField("Open SW CR Count For FBI", text);}
public void setDefectCount(String text) {im.setField("Defect Count", text);}
public void setPlannedRequirementFreezeDate(String text) {im.setField("Planned Requirement Freeze Date", text);}
public void setPlannedSpecificationFreezeDate(String text) {im.setField("Planned Specification Freeze Date", text);}
public void setPlannedIntDeliveryDate(String text) {im.setField("Planned Int Delivery Date", text);}
public void setPlannedMainDeliveryDate(String text) {im.setField("Planned Main Delivery Date", text);}
public void setLibRelease(String text) {im.setField("Lib Release", text);}
public void setMANReqReleaseLink(String text) {im.setField("MANReq Release Link", text);}
public void setMANReqReleaseID(String text) {im.setField("MANReq Release ID", text);}
public void setMANPartNumber(String text) {im.setField("MAN Part Number", text);}
public void setSpecificationLink(String text) {im.setField("Specification Link", text);}
public void setSubProjects(String text) {im.setField("Sub Projects", text);}
public void setSubProjectFor(String text) {im.setField("Sub Project For", text);}
public void setCRPhase(String text) {im.setField("CR Phase", text);}
public void setSWCRs(String text) {im.setField("SW CRs", text);}
public void setSWCRFor(String text) {im.setField("SW CR For", text);}
public void setTestPlan(String text) {im.setField("Test Plan", text);}
public void setRelease(String text) {im.setField("Release", text);}
public void setDetermines(String text) {im.setField("Determines", text);}
public void setDeterminedBy(String text) {im.setField("Determined By", text);}
public void setIsMeaningful(String text) {im.setField("Is Meaningful", text);}
public void setProjectID(String text) {im.setField("Project ID", text);}
public void setSWCRForCount(String text) {im.setField("SW CR For Count", text);}
public void setAffectedReleases(String text) {im.setField("Affected Releases", text);}
public void setTargetReleases(String text) {im.setField("Target Releases", text);}
public void setAuthor(String text) {im.setField("Author", text);}
public void setReleaseToDefects(String text) {im.setField("Release To Defects", text);}
public void setDefectToReleases(String text) {im.setField("Defect To Releases", text);}
public void setReleaseToChangeRequests(String text) {im.setField("Release To Change Requests", text);}
public void setChangeRequestToReleases(String text) {im.setField("Change Request To Releases", text);}
public void setSupplierUser(String text) {im.setField("Supplier User", text);}
public void setMainChangeRequestID(String text) {im.setField("Main Change Request ID", text);}
public void setMainChangeRequestSummary(String text) {im.setField("Main Change Request Summary", text);}
public void setMainChangeRequestState(String text) {im.setField("Main Change Request State", text);}
public void setPageOrientation(String text) {im.setField("Page Orientation", text);}
public void setFBISummary(String text) {im.setField("FBI Summary", text);}
public void setFBIID(String text) {im.setField("FBI ID", text);}
public void setEMailSubject(String text) {im.setField("E-Mail Subject", text);}
public void setEMailBody(String text) {im.setField("E-Mail Body", text);}
public void setSupplierSourceDirectory(String text) {im.setField("Supplier Source Directory", text);}
public void setLibReleasetoSWChangeRequests(String text) {im.setField("Lib Release to SW Change Requests", text);}
public void setSWChangeRequesttoLibReleases(String text) {im.setField("SW Change Request to Lib Releases", text);}
public void setRelatedSWCRs(String text) {im.setField("Related SW CRs", text);}
public void setRelatedSWCRsBW(String text) {im.setField("Related SW CRs'", text);}
public void setDeliveryDate(String text) {im.setField("Delivery Date", text);}
public void setUsedBaselineSW(String text) {im.setField("Used Baseline SW", text);}
public void setToolVersion(String text) {im.setField("Tool Version", text);}
public void setCompiler(String text) {im.setField("Compiler", text);}
public void setBuildScope(String text) {im.setField("Build Scope", text);}
public void setSourceLinkBuild(String text) {im.setField("Source Link Build", text);}
public void setGetSummary(String text) {im.setField("Get Summary", text);}
public void setSendEMail(String text) {im.setField("Send E-Mail", text);}
public void setRelatedCRID(String text) {im.setField("Related CR ID", text);}
public void setRelatedCRSummary(String text) {im.setField("Related CR Summary", text);}
public void setRelatedCRMANReqHyperlink(String text) {im.setField("Related CR MANReq Hyperlink", text);}
public void setDefectsToFBIs(String text) {im.setField("Defects To FBIs", text);}
public void setFBIsToDefects(String text) {im.setField("FBIs To Defects", text);}
public void setRelatedDefects(String text) {im.setField("Related Defects", text);}
public void setRelatedDefectsBW(String text) {im.setField("Related Defects'", text);}
public void setPlannedApprovalIntExportDate(String text) {im.setField("Planned Approval Int Export Date", text);}
public void setPlannedIntExportDate(String text) {im.setField("Planned Int Export Date", text);}
public void setPlannedApprovalFinalExportDate(String text) {im.setField("Planned Approval Final Export Date", text);}
public void setPlannedFinalExportDate(String text) {im.setField("Planned Final Export Date", text);}
public void setDocumentClassification(String text) {im.setField("Document Classification", text);}
public void setRelatedCRMANReqKey(String text) {im.setField("Related CR MANReq Key", text);}
public void setRelatedBuilds(String text) {im.setField("Related Builds", text);}
public void setRelatedBuildsBW(String text) {im.setField("Related Builds'", text);}
public void setSummaryHashcode(String text) {im.setField("Summary Hashcode", text);}
public void setAllRelatedProjects(String text) {im.setField("All Related Projects", text);}
public void setProjectSWChangeRequests(String text) {im.setField("Project SW Change Requests", text);}
public void setTotalPendingSWChangeRequestCount(String text) {im.setField("Total Pending SW Change Request Count", text);}
public void setTotalOpenSWChangeRequestCount(String text) {im.setField("Total Open SW Change Request Count", text);}
public void setTotalClosedSWChangeRequestCount(String text) {im.setField("Total Closed SW Change Request Count", text);}
public void setTotalSWChangeRequestCount(String text) {im.setField("Total SW Change Request Count", text);}
public void setValue(String text) {im.setField("Value", text);}
public void setCounter(String text) {im.setField("Counter", text);}
public void setChartName(String text) {im.setField("Chart Name", text);}
public void setTargetFBI(String text) {im.setField("Target FBI", text);}
public void setOpenSWCRCountForRelease(String text) {im.setField("Open SW CR Count For Release", text);}
public void setClosedSWCRCountForRelease(String text) {im.setField("Closed SW CR Count For Release", text);}

}
