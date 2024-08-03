package Exercise_5;

public class Task {
    private int taskId;
    private String taskName;
    private String status;
    private Task next;

    public Task(int id, String name, String status){
        this.taskId = id;
        this.taskName = name;
        this.status = status;
        this.next = null;
    }

    public void setTaskId(int taskId) { this.taskId = taskId;}
    public int getTaskId() { return taskId;}

    public void setTaskName(String taskName){ this.taskName = taskName;}
    public String getTaskName(){return taskName;}

    public void setStatus(String status) {this.status = status; }
    public String getStatus() { return status;}

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    private static Task head = null;

    //Adding element
    private static void addTask(Task newTask){
        if (head == null) head = newTask;
        else {
            Task current = head;
            while (current.next != null){
                current = current.next;
            }
            current.next = newTask;
        }
    }

    //Searching element
    private static Task searchTask(int id){
        Task current = head;
        while (current != null) {
            if (current.getTaskId() == id) return current;
            current = current.next;
        }
        return null;
    }

    //Traversing element
    private static void traverse(){
        Task current = head;
        while (current != null){
            System.out.println(current);
            current = current.next;
        }
    }

    //Deleting element
    private static void deleteTask(int id){
        Task delNode = searchTask(id);
        if (delNode == null) return;
        if (head.getTaskId() == id) head = head.next;

        Task prev = null;
        Task current = head;

        while(current != null && current.getTaskId() != id){
            prev = current;
            current = current.next;
        }

        prev.next = delNode.next;
    }

    public static void main(String[] args){

        //Adding tasks
        Task.addTask(new Task(1, "Design", "Pending"));
        Task.addTask(new Task(2, "Development", "In progress"));
        Task.addTask(new Task(3, "Deployment", "Pending"));

        //Traversing
        traverse();

        //Searching task
        Task result = searchTask(3);
        System.out.println(result);

        //Deleting tasks
        deleteTask(2);

        traverse();
    }

    
}
