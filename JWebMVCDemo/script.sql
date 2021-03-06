USE [JWebMVCDemo]
GO
/****** Object:  Table [dbo].[StudentTBL]    Script Date: 03/18/2017 13:53:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[StudentTBL](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](150) NOT NULL,
	[Gender] [bit] NOT NULL,
	[DOB] [date] NOT NULL,
 CONSTRAINT [PK_StudentTBL] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[StudentTBL] ON
INSERT [dbo].[StudentTBL] ([Id], [Name], [Gender], [DOB]) VALUES (2, N'Mr B - Updated', 0, CAST(0x323C0B00 AS Date))
INSERT [dbo].[StudentTBL] ([Id], [Name], [Gender], [DOB]) VALUES (5, N'Ms C', 1, CAST(0x303C0B00 AS Date))
SET IDENTITY_INSERT [dbo].[StudentTBL] OFF
/****** Object:  Table [dbo].[AccountTBL]    Script Date: 03/18/2017 13:53:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AccountTBL](
	[username] [varchar](150) NOT NULL,
	[password] [varchar](150) NOT NULL,
 CONSTRAINT [PK_AccountTBL] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[AccountTBL] ([username], [password]) VALUES (N'sonnt', N'12345678')
