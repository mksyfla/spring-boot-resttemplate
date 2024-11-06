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
        data: "id"
      },
      {
        data: "name"
      },
      {
        data: null,
        render: (data) => `
          <div class="d-flex justify-content-center gap-3">
            <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#detail-region" onclick=getById(${data.id})>
              Detail
            </button>
            <button type="button" class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#update-region" onclick=beforeUpdate(${data.id})>
              Update
            </button>
            <button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#delete-region" onclick=deleteRegion(${data.id})>
              Delete
            </button>
          </div>
        `
      }
    ]
  });
});

$("#create-region-button").click((event) => {
  event.preventDefault();

  const name = $("#create-name").val();
  
  if (name === "" || name === null) {
    Swal.fire({
      position: "center",
      icon: "error",
      title: "Please fill the emplty field!!!",
      showConfirmButton: false,
      timer: 1000
    });
    return
  }

  $.ajax({
    method: "POST",
    url: "/api/region",
    dataType: "json",
    contentType: "application/json",
    data: JSON.stringify({
      name
    }),
    success: (res) => {
      $("#create-region").modal("hide");
      $("#tb-region").DataTable().ajax.reload();

      Swal.fire({
        position: "center",
        icon: "success",
        title: "Region created successfully",
        showConfirmButton: false,
        timer: 1000,
      });
      
      $("#create-name").val("");
    },
    error: (err) => console.log(err),
  });
});

function getById(id) {
  $.ajax({
    method: "GET",
    url: `/api/region/${id}`,
    dataType: "json",
    contentType: "application/json",
    success: (res) => {
      $("#detail-id").html(res.id);
      $("#detail-name").html(res.name);
      $("#detail-action").html(`
        <button type="button" class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#update-region" onclick=beforeUpdate(${res.id})>
          Update
        </button>
        <button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#delete-region" onclick=deleteRegion(${res.id})>
          Delete
        </button>
      `);
    },
    error: (err) => console.log(err),
  });
}

function beforeUpdate(id) {
  $.ajax({
      method: "GET",
      url: `/api/region/${id}`,
      dataType: "json",
      contentType: "application/json",
      success: (res) => {
        $("#update-id").val(res.id);
        $("#update-name").val(res.name);
      },
      error: (err) => console.log(err),
    })
}

$("#update-region-button").click((event) => {
  event.preventDefault();

  const id = $("#update-id").val();
  const name = $("#update-name").val();
  
  if (name === "" || name === null) {
    Swal.fire({
      position: "center",
      icon: "error",
      title: "Please fill the emplty field!!!",
      showConfirmButton: false,
      timer: 1000
    });
    return
  }

  $.ajax({
    method: "PUT",
    url: `/api/region/${id}`,
    dataType: "json",
    contentType: "application/json",
    data: JSON.stringify({ name }),
    success: (res) => {
      console.log(res)
      $("#update-region").modal("hide");
      $("#tb-region").DataTable().ajax.reload();

      Swal.fire({
        position: "center",
        icon: "success",
        title: "Region updated successfully",
        showConfirmButton: false,
        timer: 1000,
      });
      
      $("#update-id").val("");
      $("#update-name").val("");
    },
    error: (err) => console.log(err),
  });
});

function deleteRegion(id) {
  const confirmationButton = Swal.mixin({
    customClass: {
      confirmButton: "btn btn-success btn-sm ms-3",
      cancelButton: "btn btn-danger btn-sm"
    },
    buttonStyling: false,
  });

  confirmationButton
    .fire({
      text: "Are you sure want to delete this?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Delete",
      cancelButtonText: "Cancel",
      reverseButtons: true,
    })
    .then((res) => {
      if (res.isConfirmed) {
        $.ajax({
          method: "DELETE",
          url: `/api/region/${id}`,
          dataType: "JSON",
          contentType: "application/json",
          success: (res) => {
            $("#tb-region").DataTable().ajax.reload();
          },
          error: (err) => console.log(err),
        });
        swalWithBootstrapButtons.fire({
          title: "Deleted!",
          text: "Your Region has been deleted.",
          icon: "success",
        });
      } else if (
        /* Read more about handling dismissals below */
        res.dismiss === Swal.DismissReason.cancel
      ) {
        swalWithBootstrapButtons.fire({
          title: "Cancelled",
          text: "Your Region is safe :)",
          icon: "error",
        });
      }
    })
}
