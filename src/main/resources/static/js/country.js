$(document).ready(() => {
  $("#tb-country").DataTable({
    ajax: {
      method: "GET",
      url: "/api/country",
      dataSrc: ""
    },
    columnDefs: [{ className: "text-center", targets: "_all" }],
    columns: [
      {
        data: null,
        render: (data, type, full, meta) => meta.row + 1
      },
      { data: "countryName" },
      { data: "countryCode" },
      { data: "regionName" },
      {
        data: null,
        render: (data) => `
          <div class="d-flex justify-content-center gap-3">
            <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#detail-country" onclick=getCountryById(${data.countryId})>
              Detail
            </button>
            <button type="button" class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#update-country" onclick=prevCountry(${data.countryId})>
              Update
            </button>
            <button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#delete-country" onclick=deleteCountry(${data.countryId})>
              Delete
            </button>
          </div>
        `
      }
    ]
  });
});

async function selectRegion(regionId) {
  const res = await getAllRegion();

  let option = `<option disabled value="" ${!regionId ? "selected" : ""}>Choose...</option>`

  for (const r of res) {
    option += `<option ${regionId == r.id ? "selected" : ""} value=${r.id} >${r.name}</option>`;
  }

  return option;
}

async function getAllRegion() {
  const settings = new Settings("GET", `/api/region`, "json", "application/json");
  const res = await requestAjax(settings);

  return res;
}

function call() {
  return $("#update-region-country").html();
}

async function prevCountry(id) {
  const settings = new Settings("GET", `/api/country/${id}`, "json", "application/json");
  const res = await requestAjax(settings);
  const region = await selectRegion(res.regionId);

  $("#update-id-country").val(res.countryId);
  $("#update-code-country").val(res.countryCode);
  $("#update-name-country").val(res.countryName);
  $("#update-region-country").html(region);
}

$("#update-country-button").click(async (event) => {
  event.preventDefault();

  const id = $("#update-id-country").val();
  const code = $("#update-code-country").val();
  const name = $("#update-name-country").val();
  const regionId = $("#update-region-country").val();
  
  if (name === "" || name === null) return;

  const data = JSON.stringify({ id, code, name, regionId });

  const settings = new Settings("PUT", `/api/country/${id}`, "json", "application/json", data);
  await requestAjax(settings);

  $("#update-country").modal("hide");
  $("#tb-country").DataTable().ajax.reload();

  Swal.fire({
    position: "center",
    icon: "success",
    title: "Region updated successfully",
    showConfirmButton: false,
    timer: 1000,
  });
  
  $("#update-id-country").val("");
  $("#update-code-country").val("");
  $("#update-name-country").val("");
  $("#update-region-country").val("");
});

async function createCountry() {
  const region = await selectRegion();

  $("#create-region-country").html(region);
}

$("#create-country-button").click(async (event) => {
  event.preventDefault();

  const code = $("#create-code-country").val();
  const name = $("#create-name-country").val();
  const regionId = $("#create-region-country").val();
  
  if (name === "" || name === null) return;

  const data = JSON.stringify({ code, name, regionId });

  const settings = new Settings("POST", `/api/country`, "json", "application/json", data);
  await requestAjax(settings);

  $("#create-country").modal("hide");
  $("#tb-country").DataTable().ajax.reload();

  Swal.fire({
    position: "center",
    icon: "success",
    title: "Region created successfully",
    showConfirmButton: false,
    timer: 1000,
  });
  
  $("#create-id-country").val("");
  $("#create-code-country").val("");
  $("#create-name-country").val("");
  $("#create-region-country").val("");
});

async function deleteCountry(id) {
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
    const settings = new Settings("DELETE", `/api/country/${id}`, "json", "application/json");
    await requestAjax(settings);

    $("#tb-country").DataTable().ajax.reload();

    conButton.fire({
      title: "Deleted!",
      text: "Your country has been deleted.",
      icon: "success",
    });
  } else if (conResult.dismiss === Swal.DismissReason.cancel) {
    conButton.fire({
      title: "Cancelled",
      text: "Your country will not be deleted",
      icon: "error",
    });
  }
}

async function getCountryById(id) {
  const settings = new Settings("GET", `/api/country/${id}`, "json", "application/json");
  const res = await requestAjax(settings);

  $("#detail-id-country").html(res.countryId);
  $("#detail-name-country").html(res.countryName);
  $("#detail-code-country").html(res.countryCode);
  $("#detail-region-country").html(res.regionName);
  $("#detail-action-country").html(`
    <button type="button" class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#update-country" onclick=prevCountry(${res.countryId})>
      Update
    </button>
    <button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#delete-country" onclick=deleteCountry(${res.countryId})>
      Delete
    </button>
  `);
}