const name = "小弟";
const age = 18;
const uri = "http://localhost:8080/test/get_name";
axios
  .post(uri, {
    name,
    age,
  })
  .then((response) => {
    // handle success
    console.log(response.data);
  })
  .catch((error) => {
    // handle error
    console.log(error);
  });
