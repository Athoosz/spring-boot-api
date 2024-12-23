const url = "http://localhost:8080/task/user/1";

function hideLoader() {
   document.getElementById("loading").style.display = "none";
}

function show(tasks) {
    let tab = `<thead class="thead-light">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Title</th>
                    <th scope="col">Description</th>
                    <th scope="col">Username</th>
                    <th scope="col">User Id</th>
                    <th scope="col">Actions</th>
                </tr>
            </thead>`;
    for (let task of tasks) {
        tab += `<tr>
                    <th scope="row">${task.id}</th>
                    <td>${task.title}</td>
                    <td>${task.description}</td>
                    <td>${task.user.username}</td>
                    <td>${task.user.id}</td>
                    <td>
                        <button class="btn btn-success btn-sm" onclick="editTask(${task.id})">Edit</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteTask(${task.id})">Delete</button>
                    </td>
                </tr>`;
    }

   document.getElementById("tasks").innerHTML = tab;
}

async function getApi(url) {
   const response = await fetch(url, { method: "GET" });

   var data = await response.json();
   console.log(data);
   if (response) {
      hideLoader();
      show(data);
   }
}

getApi(url);