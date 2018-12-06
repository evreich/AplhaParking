using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace AlphaParking.Models.Migrations
{
    public partial class Init : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "ParkingSpaces",
                columns: table => new
                {
                    Number = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ParkingSpaces", x => x.Number);
                });

            migrationBuilder.CreateTable(
                name: "Roles",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    Name = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Roles", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Users",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    Login = table.Column<string>(nullable: true),
                    Password = table.Column<string>(nullable: true),
                    FIO = table.Column<string>(nullable: true),
                    Address = table.Column<string>(nullable: true),
                    Phone = table.Column<string>(nullable: true),
                    Email = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Users", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Cars",
                columns: table => new
                {
                    Number = table.Column<string>(nullable: false),
                    Brand = table.Column<string>(nullable: true),
                    Model = table.Column<string>(nullable: true),
                    UserId = table.Column<Guid>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Cars", x => x.Number);
                    table.ForeignKey(
                        name: "FK_Cars_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "Users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "UserRoles",
                columns: table => new
                {
                    UserId = table.Column<Guid>(nullable: false),
                    RoleId = table.Column<Guid>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_UserRoles", x => new { x.RoleId, x.UserId });
                    table.ForeignKey(
                        name: "FK_UserRoles_Roles_RoleId",
                        column: x => x.RoleId,
                        principalTable: "Roles",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_UserRoles_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "Users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "ParkingSpaceCars",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    ParkingSpaceNumber = table.Column<int>(nullable: false),
                    CarNumber = table.Column<string>(nullable: true),
                    StartParkingTime = table.Column<TimeSpan>(nullable: false),
                    EndParkingTime = table.Column<TimeSpan>(nullable: false),
                    IsMainParkingSpace = table.Column<bool>(nullable: false),
                    CheckIn = table.Column<bool>(nullable: false),
                    DelegatedCarNumber = table.Column<string>(nullable: true),
                    StartDelegatedDate = table.Column<DateTime>(nullable: true),
                    EndDelegatedDate = table.Column<DateTime>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ParkingSpaceCars", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ParkingSpaceCars_Cars_CarNumber",
                        column: x => x.CarNumber,
                        principalTable: "Cars",
                        principalColumn: "Number",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ParkingSpaceCars_Cars_DelegatedCarNumber",
                        column: x => x.DelegatedCarNumber,
                        principalTable: "Cars",
                        principalColumn: "Number",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ParkingSpaceCars_ParkingSpaces_ParkingSpaceNumber",
                        column: x => x.ParkingSpaceNumber,
                        principalTable: "ParkingSpaces",
                        principalColumn: "Number",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.InsertData(
                table: "Roles",
                columns: new[] { "Id", "Name" },
                values: new object[] { new Guid("00000002-0000-0000-0000-000000000000"), "Employee" });

            migrationBuilder.InsertData(
                table: "Roles",
                columns: new[] { "Id", "Name" },
                values: new object[] { new Guid("00000001-0000-0000-0000-000000000000"), "Manager" });

            migrationBuilder.CreateIndex(
                name: "IX_Cars_UserId",
                table: "Cars",
                column: "UserId");

            migrationBuilder.CreateIndex(
                name: "IX_ParkingSpaceCars_CarNumber",
                table: "ParkingSpaceCars",
                column: "CarNumber");

            migrationBuilder.CreateIndex(
                name: "IX_ParkingSpaceCars_DelegatedCarNumber",
                table: "ParkingSpaceCars",
                column: "DelegatedCarNumber");

            migrationBuilder.CreateIndex(
                name: "IX_ParkingSpaceCars_ParkingSpaceNumber",
                table: "ParkingSpaceCars",
                column: "ParkingSpaceNumber");

            migrationBuilder.CreateIndex(
                name: "IX_UserRoles_UserId",
                table: "UserRoles",
                column: "UserId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ParkingSpaceCars");

            migrationBuilder.DropTable(
                name: "UserRoles");

            migrationBuilder.DropTable(
                name: "Cars");

            migrationBuilder.DropTable(
                name: "ParkingSpaces");

            migrationBuilder.DropTable(
                name: "Roles");

            migrationBuilder.DropTable(
                name: "Users");
        }
    }
}
