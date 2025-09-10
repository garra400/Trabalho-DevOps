{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = [
    pkgs.jdk17
    pkgs.maven
    pkgs.spring-boot-cli
    pkgs.postgresql
    pkgs.lombok
  ];

  shellHook = ''
    echo "Java: $(java -version 2>&1 | head -n1)"
    echo "Maven: $(mvn -v | head -n1)"
    echo "Spring Boot CLI: $(spring --version)"
    echo "PostgreSQL: $(psql --version)"
    echo "Lombok is available in: ${pkgs.lombok}/share/java/lombok.jar"
  '';
}