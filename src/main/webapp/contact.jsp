<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8" />
    <title>Sherlock - on-line library</title>
    <script src="https://use.fontawesome.com/releases/v5.15.4/js/all.js" crossorigin="anonymous"></script>
    <link href="css/index-styles.css" rel="stylesheet" />
</head>
<body>
    <jsp:include page="menu.jsp" />
    <section class="page-section-books novelty" id="contact">
<%--    <section class="page-section" id="contact">--%>
  <div class="container">
    <!-- Contact Section Heading-->
    <h2
            class="page-section-heading text-center text-uppercase text-secondary mb-0">Форма связи</h2>
    <!-- Icon Divider-->
    <div class="divider-custom">
      <div class="divider-custom-line"></div>
      <div class="divider-custom-icon">
        <i class="fas fa-star"></i>
      </div>
      <div class="divider-custom-line"></div>
    </div>
    <!-- Contact Section Form-->
    <div class="row justify-content-center">
      <div class="col-lg-8 col-xl-7">
        <form id="contactForm" data-sb-form-api-token="API_TOKEN">
          <!-- Name input-->
          <div class="form-floating mb-3">
            <input class="form-control" id="name" type="text"
                   placeholder="Enter your name..." data-sb-validations="required" />
            <label for="name">Фамилия, имя</label>
            <div class="invalid-feedback" data-sb-feedback="name:required">A
              name is required.</div>
          </div>
          <!-- Email address input-->
          <div class="form-floating mb-3">
            <input class="form-control" id="email" type="email"
                   placeholder="name@example.com"
                   data-sb-validations="required,email" /> <label for="email">Email</label>
            <div class="invalid-feedback" data-sb-feedback="email:required">An
              email is required.</div>
            <div class="invalid-feedback" data-sb-feedback="email:email">Email
              is not valid.</div>
          </div>
          <!-- Phone number input-->
          <div class="form-floating mb-3">
            <input class="form-control" id="phone" type="tel"
                   placeholder="(123) 456-7890" data-sb-validations="required" />
            <label for="phone">Номер телефона</label>
            <div class="invalid-feedback" data-sb-feedback="phone:required">A
              phone number is required.</div>
          </div>
          <!-- Message input-->
          <div class="form-floating mb-3">
							<textarea class="form-control" id="message" type="text"
                                      placeholder="Enter your message here..." style="height: 10rem"
                                      data-sb-validations="required"></textarea>
            <label for="message">Сообщение</label>
            <div class="invalid-feedback" data-sb-feedback="message:required">A
              message is required.</div>
          </div>
          <div class="d-none" id="submitSuccessMessage">
            <div class="text-center mb-3">
              <div class="fw-bolder">Form submission successful!</div>
              To activate this form, sign up at <br /> <a
                    href="https://startbootstrap.com/solution/contact-forms">https://startbootstrap.com/solution/contact-forms</a>
            </div>
          </div>
          <!-- Submit error message-->
          <!---->
          <!-- This is what your users will see when there is-->
          <!-- an error submitting the form-->
          <div class="d-none" id="submitErrorMessage">
            <div class="text-center text-danger mb-3">Error sending
              message!</div>
          </div>
          <!-- Submit Button-->
          <button class="btn btn-primary btn-xl disabled" id="submitButton"
                  type="submit">Отправить</button>
        </form>
      </div>
    </div>
  </div>
</section>
    <jsp:include page="footer.jsp" />
</body>
</html>
