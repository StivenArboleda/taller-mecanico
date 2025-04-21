window.onload = () => {
  window.ui = SwaggerUIBundle({
    url: "/v3/api-docs",
    dom_id: "#swagger-ui",
    presets: [SwaggerUIBundle.presets.apis],
    layout: "BaseLayout",
    docExpansion: "none",
    deepLinking: true,
    operationsSorter: "alpha",
  });

  // Personalización del título y subtítulo
  const title = document.createElement("h1");
  title.innerText = "Sistema de Gestión de Taller Mecánico";
  title.style.color = "#C6C6C6";
  title.style.marginLeft = "10px";

  const subtitle = document.createElement("p");
  subtitle.innerText = "API para la administración de clientes, vehículos, citas, reportes y todo lo relacionado";
  subtitle.style.color = "#C6C6C6";
  subtitle.style.marginLeft = "10px";

  const header = document.querySelector(".topbar");
  header.innerHTML = ""; // Limpia lo anterior
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
};
