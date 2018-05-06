package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.entity.TaskStatus;
import ru.compito.taskmanager.repository.BoardStatusRepository;
import ru.compito.taskmanager.repository.TaskRepository;
import ru.compito.taskmanager.repository.TaskStatusRepository;
import ru.compito.taskmanager.service.TaskStatusService;

import java.util.List;

@Service(value = "TaskStatusService")
@Transactional
public class TaskStatusServiceImpl implements TaskStatusService {

    @Autowired
    private BoardStatusRepository boardStatusRepository;
    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskStatus getOne(Integer Id) {
        return taskStatusRepository.findOne(Id);
    }

    @Override
    public List<TaskStatus> findAll() {
        return taskStatusRepository.findAll();
    }

    @Override
    public TaskStatus save(TaskStatus taskStatus) {
        return taskStatusRepository.save(taskStatus);
    }

    @Override
    public TaskStatus update(TaskStatus updatedTaskStatus) {
       return taskStatusRepository.save(updatedTaskStatus);
    }

    @Override
    public void delete(Integer taskStatusId) {
        TaskStatus taskStatus = taskStatusRepository.getOne(taskStatusId);
        boardStatusRepository.deleteAllByTaskStatus(taskStatus);
        List<Task> tasks = taskRepository.findAllByCurrentStatus(taskStatus);
        for(Task task:tasks){
            task.setCurrentStatus(null);
            taskRepository.save(task);
        }
        taskStatusRepository.delete(taskStatusId);
    }
}
