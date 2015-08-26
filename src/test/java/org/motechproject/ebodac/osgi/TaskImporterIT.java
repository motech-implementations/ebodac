package org.motechproject.ebodac.osgi;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.ebodac.importer.TaskImporter;
import org.motechproject.tasks.domain.Task;
import org.motechproject.tasks.service.TaskService;
import org.motechproject.testing.osgi.BasePaxIT;
import org.motechproject.testing.osgi.container.MotechNativeTestContainerFactory;
import org.ops4j.pax.exam.ExamFactory;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerSuite;
import org.osgi.framework.FrameworkUtil;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerSuite.class)
@ExamFactory(MotechNativeTestContainerFactory.class)
public class TaskImporterIT extends BasePaxIT {

    @Inject
    private TaskService taskService;

    private TaskImporter taskImporter;

    private List<Long> tasksId;

    @Before
    public void cleanBefore() throws Exception {
        taskImporter = new TaskImporter();
        taskImporter.bind(taskService, null);
        tasksId = new ArrayList<Long>();
    }

    @After
    public void cleanAfter() {
        for(Long id : tasksId) {
            taskService.deleteTask(id);
        }
    }

    @Test
    public void shouldImportTasks() {
        taskImporter.importTasks(FrameworkUtil.getBundle(getClass()));
        List<Task> tasks = taskService.getAllTasks();

        List<Task> foundTasks = taskService.findTasksByName("test");
        assertEquals(1, foundTasks.size());
        assertTrue(tasks.contains(foundTasks.get(0)));
        tasksId.add(foundTasks.get(0).getId());
    }
}
