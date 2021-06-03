package com.afeka.scrummaster.layout;

public enum TaskStatus {
	BACKLOG, TODO, IN_PROGRESS, REVIEW, DONE;

	public String text() {
		switch (this) {
			case BACKLOG:
				return "Backlog";
			case TODO:
				return "To Do";
			case IN_PROGRESS:
				return "In Progress";
			case REVIEW:
				return "Review";
			case DONE:
				return "Done";
			default:
				return null;
		}
	}
}
