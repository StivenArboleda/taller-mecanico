
window.onload = () => {
  const link = document.createElement("link");
  link.href = "https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap";
  link.rel = "stylesheet";
  document.head.appendChild(link);

  window.ui = SwaggerUIBundle({
    url: "/v3/api-docs",
    dom_id: "#swagger-ui",
    presets: [SwaggerUIBundle.presets.apis],
    layout: "BaseLayout",
    docExpansion: "none",
    deepLinking: true,
    operationsSorter: "alpha",
  });

  const title = document.createElement("h1");
  title.innerText = "Sistema de Gestión de Taller Mecánico";
  title.style.color = "#C6C6C6";
  title.style.marginLeft = "10px";
  title.style.fontFamily = "Poppins, sans-serif";

  const subtitle = document.createElement("p");
  subtitle.innerText = "API para la administración de clientes, vehículos, citas, reportes y todo lo relacionado";
  subtitle.style.color = "#C6C6C6";
  subtitle.style.marginLeft = "10px";
  subtitle.style.fontFamily = "Poppins, sans-serif";

  const header = document.querySelector(".topbar");
  header.innerHTML = "";
  const logo = document.createElement("img");
  logo.src = "/swagger-ui/logo.png";
  logo.alt = "Logo Stiven Arboleda";
  logo.style.height = "40px";
  logo.style.marginRight = "10px";

  const container = document.createElement("div");
  container.style.display = "flex";
  container.style.alignItems = "center";
  container.appendChild(logo);
  container.appendChild(title);

  header.appendChild(container);
  header.appendChild(subtitle);

  const footer = document.createElement("div");
  footer.innerText = "© 2025 Stiven Arboleda - Todos los derechos reservados";
  footer.style.textAlign = "center";
  footer.style.marginTop = "40px";
  footer.style.color = "#888";
  footer.style.fontSize = "12px";
  footer.style.fontFamily = "Poppins, sans-serif";
  document.body.appendChild(footer);
};
