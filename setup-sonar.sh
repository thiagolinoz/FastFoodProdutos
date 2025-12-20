#!/bin/bash

echo "🔧 Configurando SonarQube para FastFood Produtos"
echo ""

# Cores
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Verificar se o SonarQube está rodando
echo "1️⃣  Verificando SonarQube..."
if curl -s http://localhost:9000/api/system/status > /dev/null 2>&1; then
    echo -e "${GREEN}✓ SonarQube está rodando!${NC}"
else
    echo -e "${RED}✗ SonarQube não está rodando${NC}"
    echo ""
    echo "Inicie o SonarQube com:"
    echo "  docker-compose -f docker-compose-sonarqube.yaml up -d"
    exit 1
fi

echo ""
echo "2️⃣  Para configurar o projeto:"
echo ""
echo -e "${YELLOW}Acesse: http://localhost:9000${NC}"
echo ""
echo "Passos:"
echo "  1. Login: admin / admin (ou sua senha alterada)"
echo "  2. Clique em 'Create Project' → 'Manually'"
echo "  3. Project key: fastfood-produtos"
echo "  4. Display name: FastFood Produtos"
echo "  5. Clique em 'Set Up'"
echo "  6. Escolha 'Locally'"
echo "  7. Generate Token:"
echo "     - Nome: fastfood-produtos-token"
echo "     - Clique em 'Generate'"
echo "     - COPIE o token gerado!"
echo "  8. Escolha 'Maven'"
echo ""
echo "3️⃣  Depois execute:"
echo ""
echo -e "${GREEN}mvn clean verify sonar:sonar \\"
echo "  -Dsonar.projectKey=fastfood-produtos \\"
echo "  -Dsonar.host.url=http://localhost:9000 \\"
echo -e "  -Dsonar.token=SEU_TOKEN_AQUI${NC}"
echo ""
