
# what is active object pattern?

The Active Object pattern is a design pattern in software engineering that aims to decouple the method invocation from method execution in a way that allows for better concurrency, asynchronous processing, and control over object behavior. This pattern is particularly useful when dealing with scenarios where multiple threads or asynchronous tasks need to interact with objects in a controlled and synchronized manner.

In the Active Object pattern, an "active object" encapsulates a method invocation as a request object. These requests are then queued in the active object's internal queue, and a separate thread (often referred to as the "scheduler" or "dispatcher") manages the execution of these requests one by one. This separation of method invocation and execution allows for the following benefits:

1. **Concurrency Control**: The active object pattern ensures that method invocations are serialized and executed in a controlled manner. This helps prevent issues related to concurrent access to shared resources, leading to more predictable behavior.

2. **Asynchronous Execution**: The active object can perform operations asynchronously. The caller of a method doesn't need to wait for the method to complete; they can continue their work while the active object processes the request.

3. **Synchronization**: The pattern inherently provides synchronization of method invocations, as they are processed one at a time. This simplifies the management of shared resources and synchronization mechanisms.

4. **Decoupling**: The pattern separates the caller from the callee's execution. Callers enqueue requests on the active object and don't need to be concerned about the actual execution details.

5. **Enhanced Control**: The active object can provide additional control over method execution, such as prioritization of requests or even logging and error handling.

In terms of implementation, the active object pattern typically involves the following components:

1. **Interface**: Defines the methods that can be invoked by clients.

2. **Concrete Active Object**: Implements the interface and encapsulates the method invocations as request objects. It maintains an internal queue of requests to be executed.

3. **Scheduler/Dispatcher**: Manages the execution of requests from the queue in a controlled manner. It can use threading or asynchronous mechanisms to execute requests.

4. **Request Objects**: Represent method invocations along with their parameters and context. These are added to the queue for execution.

The Active Object pattern is particularly valuable in scenarios where responsiveness, concurrency, and separation of concerns are important, such as in GUI frameworks, video games, and distributed systems.

Remember that the practical implementation of the Active Object pattern might vary based on the programming language and context in which it is being used.

# How a game could use the Active Object pattern?

Implementing the Active Object pattern in a game can provide several benefits, especially in scenarios where the game world involves various entities that need to interact asynchronously and concurrently. Let's consider an example of how a game could use the Active Object pattern:

Imagine you're developing a real-time strategy (RTS) game where units need to perform various actions like moving, attacking, and gathering resources. Here's how the Active Object pattern could be applied:

1. **Interface Definition**:
   Start by defining an interface that represents the actions units can perform. For instance, you might have methods like `moveTo(x, y)`, `attack(target)`, and `gather(resource)`.

```java
public interface UnitActions {
    void moveTo(int x, int y);
    void attack(Unit target);
    void gather(Resource resource);
}
```

2. **Concrete Active Object**:
   Create a concrete class that implements the `UnitActions` interface. This class will encapsulate method invocations as request objects and manage their execution.

```java
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ActiveUnit implements UnitActions, Runnable {
    private BlockingQueue<Runnable> requestQueue = new ArrayBlockingQueue<>(10);

    @Override
    public void moveTo(int x, int y) {
        requestQueue.offer(() -> {
            // Implementation of moving logic here
        });
    }

    @Override
    public void attack(Unit target) {
        requestQueue.offer(() -> {
            // Implementation of attacking logic here
        });
    }

    @Override
    public void gather(Resource resource) {
        requestQueue.offer(() -> {
            // Implementation of gathering logic here
        });
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Runnable request = requestQueue.take();
                request.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
```

3. **Scheduler/Dispatcher**:
   You need a scheduler or dispatcher to manage the execution of requests for active units. In a game, this could be a dedicated thread pool or an event loop that processes unit actions.

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnitScheduler {
    private ExecutorService executor = Executors.newFixedThreadPool(4); // Adjust pool size as needed

    public void submitAction(ActiveUnit unit, Runnable action) {
        unit.enqueue(action);
        executor.submit(unit);
    }
}
```

4. **Using Active Objects in the Game**:
   In your game logic, you'd create instances of `ActiveUnit` for each unit and use the `UnitScheduler` to submit actions for execution.

```java
public class GameLogic {
    public static void main(String[] args) {
        UnitScheduler scheduler = new UnitScheduler();

        ActiveUnit unit1 = new ActiveUnit();
        ActiveUnit unit2 = new ActiveUnit();

        scheduler.submitAction(unit1, () -> unit1.moveTo(100, 200));
        scheduler.submitAction(unit2, () -> unit2.attack(enemyUnit));
        scheduler.submitAction(unit1, () -> unit1.gather(resource));
    }
}
```

By using the Active Object pattern in your game, you can ensure that unit actions are processed asynchronously and concurrently, enhancing the responsiveness and realism of the game world. The pattern helps manage the complex interactions between different game entities while maintaining control and synchronization.

# If the game requires 100 action unit in the same time, is that the pool size should be set to 100?

Setting the thread pool size to match the number of active units (100 in this case) might seem like a straightforward solution, but it's not always the most efficient approach. The ideal thread pool size depends on various factors, including the available hardware resources, the complexity of the actions being performed, and the nature of the game's workload.

Having a thread pool with a size of 100 might lead to high resource consumption and contention, potentially causing performance degradation or even system instability. Here are a few considerations to keep in mind:

1. **CPU and Hardware Resources**: Creating and managing a large number of threads can consume significant CPU and memory resources. If each action is computationally intensive, having too many threads can lead to resource exhaustion and reduced performance due to context switching overhead.

2. **Concurrency Limits**: Most systems have limits on the number of threads they can efficiently handle. If you exceed these limits, you might experience diminishing returns or even performance degradation.

3. **IO-Bound vs. CPU-Bound Workloads**: If the actions performed by units are primarily I/O-bound (waiting for external resources like network data), a smaller thread pool might suffice. If they are CPU-bound (computationally intensive), you need to be careful with the thread count.

4. **Asynchronous Work**: Remember that **you're already using the Active Object pattern to manage the asynchronous execution of unit actions. The thread pool's purpose is to manage concurrent execution, so you don't necessarily need one thread per unit.**

5. **Experimentation and Tuning**: It's advisable to experiment with different thread pool sizes and monitor the system's resource utilization and performance metrics. This will help you find a balance between concurrency and resource efficiency.

As a general guideline, you might start with a thread pool size that is smaller than the total number of units. For example, you could start with a thread pool size of 10 or 20 and then adjust it based on performance testing and observation.

If you find that the thread pool is becoming a bottleneck or that the performance is not meeting your expectations, you could consider approaches such as:

- Using a thread pool with a dynamic resizing mechanism that adjusts the thread count based on workload and available resources.
- Implementing workload prioritization to ensure that critical or time-sensitive actions are processed promptly.
- Applying load balancing techniques to distribute actions across a reasonable number of threads.

Remember that optimizing for concurrency and performance can be a complex task, and it often involves iteration and fine-tuning based on real-world usage patterns and performance measurements.


# 总结
active object: 适用于每个object都有自己的生命周期和action节奏的场景