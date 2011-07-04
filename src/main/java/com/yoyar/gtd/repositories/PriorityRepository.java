package com.yoyar.gtd.repositories;

import com.yoyar.gtd.entities.Priority;

public interface PriorityRepository {
	public Priority get(Priority.TYPE priorityType);
}
