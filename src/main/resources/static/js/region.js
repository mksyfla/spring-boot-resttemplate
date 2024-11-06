$(document).ready(() => {
  $("#tb-region").DataTable({
    ajax: {
      method: "GET",
      url: "/api/region",
      dataSrc: ""
    },
    columnDefs: [{ className: "text-center", targets: "_all" }],
    columns: [
      {
        data: null,
        render: (data, type, full, meta) => meta.row + 1
      },
      { data: "name" },
      {
        data: null,
        render: (data) => `
          <div class="d-flex justify-content-center gap-3">
            <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#detail-region" onclick=getRegionById(${data.id})>
              Detail
            </button>
            <button type="button" class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#update-region" onclick=prevRegion(${data.id})>
              Update
            </button>
            <button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#delete-region" onclick=deleteRegion(${data.id})>
              Delete
            </button>
          </div>
        `
      }
    ],
  });
});

$("#create-region-button").click(async (event) => {
  event.preventDefault();

  const name = $("#create-name-region").val();
  
  if (name === "" || name === null) return;
  
  const data = JSON.stringify({ name });
  
  const settings = new Settings("POST", `/api/region`, "json", "application/json", data);
  await requestAjax(settings);

  $("#create-region").modal("hide");
  $("#tb-region").DataTable().ajax.reload();

  Swal.fire({
    position: "center",
    icon: "success",
    title: "Region created successfully",
    showConfirmButton: false,
    timer: 1000,
  });
  
  $("#create-name-region").val("");
});

async function getRegionById(id) {
  const settings = new Settings("GET", `/api/region/${id}`, "json", "application/json");
  const res = await requestAjax(settings);

  $("#detail-id-region").html(res.id);
  $("#detail-name-region").html(res.name);
  $("#detail-action-region").html(`
    <button type="button" class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#update-region" onclick=prevRegion(${res.id})>
      Update
    </button>
    <button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#delete-region" onclick=deleteRegion(${res.id})>
      Delete
    </button>
  `);
}

async function prevRegion(id) {
  const settings = new Settings("GET", `/api/region/${id}`, "json", "application/json");
  const res = await requestAjax(settings);

  $("#update-id-region").val(res.id);
  $("#update-name-region").val(res.name);
}

$("#update-region-button").click(async (event) => {
  event.preventDefault();

  const id = $("#update-id-region").val();
  const name = $("#update-name-region").val();
  
  if (name === "" || name === null) return;

  const data = JSON.stringify({ name });

  const settings = new Settings("PUT", `/api/region/${id}`, "json", "application/json", data);
  await requestAjax(settings);

  $("#update-region").modal("hide");
  $("#tb-region").DataTable().ajax.reload();

  Swal.fire({
    position: "center",
    icon: "success",
    title: "Region updated successfully",
    showConfirmButton: false,
    timer: 1000,
  });
  
  $("#update-id-region").val("");
  $("#update-name-region").val("");
});

async function deleteRegion(id) {
  const conButton = Swal.mixin({
    customClass: {
      confirmButton: "btn btn-success btn-sm ms-3",
      cancelButton: "btn btn-danger btn-sm"
    },
    buttonStyling: false,
  });

  const conResult = await conButton.fire({
    text: "Are you sure want to delete this?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Delete",
    cancelButtonText: "Cancel",
    reverseButtons: true,
  })

  if (conResult.isConfirmed) {
    const settings = new Settings("DELETE", `/api/region/${id}`, "json", "application/json");
    await requestAjax(settings);

    $("#tb-region").DataTable().ajax.reload();

    conButton.fire({
      title: "Deleted!",
      text: "Your region has been deleted.",
      icon: "success",
    });
  } else if (conResult.dismiss === Swal.DismissReason.cancel) {
    conButton.fire({
      title: "Cancelled",
      text: "Your region will not be deleted",
      icon: "error",
    });
  }
}
