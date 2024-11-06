class Settings {
  constructor(method, url, dataType, contentType, data = "") {
    this.method = method;
    this.url = url;
    this.dataType = dataType;
    this.contentType = contentType;
    this.data = data;
  }
}

async function requestAjax(settings) {
  const data = $.ajax(settings)
    .done((res) => res)
    .fail((err) => console.log(err));

  return await data;
}