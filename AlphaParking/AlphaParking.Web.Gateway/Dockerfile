# Образ для запуска приложения
FROM microsoft/dotnet:2.1-aspnetcore-runtime AS base
WORKDIR /app
ENV ASPNETCORE_URLS http://*:9000
ENV ASPNETCORE_ENVIRONMENT dev
EXPOSE 9000

# Образ для сборки приложения
FROM microsoft/dotnet:2.1-sdk AS build
WORKDIR /src
COPY ["AlphaParking.Web.Gateway.csproj", "AlphaParking.Web.Gateway/"]
WORKDIR /src/AlphaParking.Web.Gateway
RUN dotnet restore "AlphaParking.Web.Gateway.csproj"
COPY . .
RUN dotnet build "AlphaParking.Web.Gateway.csproj" -c Release -o /app

FROM build AS publish
WORKDIR /src/AlphaParking.Web.Gateway
RUN dotnet publish "AlphaParking.Web.Gateway.csproj" -c Release -o /app

FROM base AS final
WORKDIR /app
COPY --from=publish /app .
ENTRYPOINT ["dotnet", "AlphaParking.Web.Gateway.dll"]