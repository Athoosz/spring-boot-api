const tasksEndpoint = "http://localhost:8080/task/user";
const taskEndpoint = "http://localhost:8080/task";

function hideLoader() {
   document.getElementById("loading").style.display = "none";
}

function show(tasks) {
    let tab = `<thead class="thead-light">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Title</th>
                    <th scope="col">Description</th>
                    <th scope="col">Options</th>
                </tr>
            </thead>`;
    for (let task of tasks) {
        tab += `<tr>
                    <th scope="row">${task.id}</th>
                    <td>${task.title}</td>
                    <td>${task.description}</td>
                    <td>
                        <button class="btn btn-danger btn-sm" onclick="deleteTask(${task.id})">Delete</button>
                    </td>
                </tr>`;
    }

   document.getElementById("tasks").innerHTML = tab;
}

async function getTasks() {
    let key = "Authorization";
    const response = await fetch(tasksEndpoint, {
      method: "GET",
      headers: new Headers({
        Authorization: localStorage.getItem(key),
      }),
    });
  
    var data = await response.json();
    console.log(data);
    if (response) hideLoader();
    show(data);
  }
  
  document.addEventListener("DOMContentLoaded", function (event) {
    if (!localStorage.getItem("Authorization"))
        window.location = "/view/login.html";

    document.getElementById("task-form").addEventListener("submit", addTask);

    getTasks();
});

async function addTask(event) {
  event.preventDefault(); 

  const title = document.getElementById("taskTitle").value;
  const description = document.getElementById("taskDescription").value;

  const response = await fetch(taskEndpoint, {
      method: "POST",
      headers: new Headers({
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("Authorization"),
      }),
      body: JSON.stringify({
          title: title,
          description: description,
      }),
  });

  if (response.ok) {
      alert("Task adicionada com sucesso!");
      location.reload(); 
  } else {
      alert("Falha ao adicionar task.");
  }
}

async function deleteTask(taskId) {
  const response = await fetch(`${taskEndpoint}/${taskId}`, {
      method: "DELETE",
      headers: new Headers({
          Authorization: localStorage.getItem("Authorization"),
      }),
  });

  if (response.ok) {
      alert("Task deletada com sucesso!");
      location.reload();
  } else {
      alert("Falha ao deletar task.");
  }
}
