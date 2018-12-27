# Образ для запуска приложения
FROM microsoft/dotnet:2.1-aspnetcore-runtime AS runtime
WORKDIR /app
ENV ASPNETCORE_URLS http://*:8182
EXPOSE 8182

# Образ для сборки приложения
FROM microsoft/dotnet:2.1-sdk AS build
WORKDIR /src
# Копируем файлы проектов, которые содержит информацию о зависимостях 
COPY ["AlphaParking.Web.Host/AlphaParking.Web.Host.csproj", "AlphaParking.Web.Host/"]
COPY ["AlphaParking.Models/AlphaParking.Models.csproj", "AlphaParking.Models/"]
COPY ["AlphaParking.BLL/AlphaParking.BLL.csproj", "AlphaParking.BLL/"]
COPY ["AlphaParking.DAL/AlphaParking.DAL.csproj", "AlphaParking.DAL/"]

# Генерация файла project.assets.json, необходимого для корректой сборки приложения и его зависимости
RUN dotnet restore "AlphaParking.Web.Host/AlphaParking.Web.Host.csproj"
COPY . .
# Сборка приложения и его зависимостей (за исключением внешних библиотек NuGet)
WORKDIR /src/AlphaParking.Web.Host
RUN dotnet build "AlphaParking.Web.Host.csproj" -c Release -o /app

# Компиляция и сборка приложения и его зависимостей в папку. 
# Создаются файлы, необходимые для развертки приложения на нек. машине 
# (включая внешние зависимости NuGet)
FROM build AS publish
WORKDIR /src/AlphaParking.Web.Host
RUN dotnet publish "AlphaParking.Web.Host.csproj" -c Release -o /app
# Запуск приложения на основе файлов, сгенерированных dotnet publish
FROM runtime AS final
WORKDIR /app
COPY --from=publish /app .
ENTRYPOINT ["dotnet", "AlphaParking.Web.Host.dll"]