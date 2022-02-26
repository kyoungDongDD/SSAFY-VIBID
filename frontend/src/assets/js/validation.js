function validateEmail(email) {
  const re = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;

  return re.test(String(email).toLowerCase());
}
function validatePassword(password) {
  const pw = password;
  var reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$/;
  if (false === reg.test(pw) || pw.search(/\s/) != -1) {
    return false;
  } else {
    console.log("비밀번호 유효성 통과");
    return true;
  }
}
export { validateEmail, validatePassword };
