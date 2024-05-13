--
-- PostgreSQL database dump
--

-- Dumped from database version 10.16
-- Dumped by pg_dump version 10.16

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: tb_admin; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_admin (
    id integer NOT NULL,
    admin_name character varying(50),
    admin_password character varying(100),
    permission character varying(50),
    add_time timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.tb_admin OWNER TO postgres;

--
-- Name: tb_admin_admin_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tb_admin_admin_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tb_admin_admin_id_seq OWNER TO postgres;

--
-- Name: tb_admin_admin_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tb_admin_admin_id_seq OWNED BY public.tb_admin.id;


--
-- Name: tb_pointrules; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_pointrules (
    id integer NOT NULL,
    rulesname character varying(255),
    counts numeric,
    increasepoint numeric
);


ALTER TABLE public.tb_pointrules OWNER TO postgres;

--
-- Name: tb_pointrules_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tb_pointrules_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tb_pointrules_id_seq OWNER TO postgres;

--
-- Name: tb_pointrules_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tb_pointrules_id_seq OWNED BY public.tb_pointrules.id;


--
-- Name: tb_pointtransactionrecords; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_pointtransactionrecords (
    id integer NOT NULL,
    userid integer,
    income_or_expense_type integer,
    point_value integer,
    point_change_type character varying(10),
    device_id character varying(255),
    outlet integer,
    advertisement_link character varying(255),
    transaction_time timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    user_name character varying(255),
    CONSTRAINT tb_pointtransactionrecords_income_or_expense_type_check CHECK ((income_or_expense_type = ANY (ARRAY[1, '-1'::integer])))
);


ALTER TABLE public.tb_pointtransactionrecords OWNER TO postgres;

--
-- Name: tb_pointtransactionrecords_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tb_pointtransactionrecords_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tb_pointtransactionrecords_id_seq OWNER TO postgres;

--
-- Name: tb_pointtransactionrecords_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tb_pointtransactionrecords_id_seq OWNED BY public.tb_pointtransactionrecords.id;


--
-- Name: tb_qrcode; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_qrcode (
    id character varying(255) NOT NULL,
    outlet numeric DEFAULT 1,
    chaddress character varying(255),
    imgname character varying(255),
    codeaddress character varying(255),
    appid character varying(255),
    saler character varying(255),
    CONSTRAINT tb_qrcode_outlet_check CHECK ((outlet = ANY (ARRAY[(1)::numeric, (2)::numeric, (3)::numeric, (4)::numeric])))
);


ALTER TABLE public.tb_qrcode OWNER TO postgres;

--
-- Name: tb_task; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_task (
    id integer NOT NULL,
    taskcounts integer,
    createtime timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.tb_task OWNER TO postgres;

--
-- Name: tb_task_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tb_task_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tb_task_id_seq OWNER TO postgres;

--
-- Name: tb_task_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tb_task_id_seq OWNED BY public.tb_task.id;


--
-- Name: tb_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_user (
    id integer NOT NULL,
    openid character varying(255),
    phone character varying(255),
    name character varying(255),
    pointbalance integer DEFAULT 100 NOT NULL,
    profile_photo character varying(255),
    task_count integer DEFAULT 0
);


ALTER TABLE public.tb_user OWNER TO postgres;

--
-- Name: tb_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tb_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tb_user_id_seq OWNER TO postgres;

--
-- Name: tb_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tb_user_id_seq OWNED BY public.tb_user.id;


--
-- Name: tb_admin id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_admin ALTER COLUMN id SET DEFAULT nextval('public.tb_admin_admin_id_seq'::regclass);


--
-- Name: tb_pointrules id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_pointrules ALTER COLUMN id SET DEFAULT nextval('public.tb_pointrules_id_seq'::regclass);


--
-- Name: tb_pointtransactionrecords id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_pointtransactionrecords ALTER COLUMN id SET DEFAULT nextval('public.tb_pointtransactionrecords_id_seq'::regclass);


--
-- Name: tb_task id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_task ALTER COLUMN id SET DEFAULT nextval('public.tb_task_id_seq'::regclass);


--
-- Name: tb_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_user ALTER COLUMN id SET DEFAULT nextval('public.tb_user_id_seq'::regclass);


--
-- Data for Name: tb_admin; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_admin (id, admin_name, admin_password, permission, add_time) FROM stdin;
1	admin	12345678	0	2024-05-10 18:12:37.009062
2	admin	12345678	0	2024-05-10 18:13:40.206997
\.


--
-- Data for Name: tb_pointrules; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_pointrules (id, rulesname, counts, increasepoint) FROM stdin;
-1234698238	string	50	0
\.


--
-- Data for Name: tb_pointtransactionrecords; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_pointtransactionrecords (id, userid, income_or_expense_type, point_value, point_change_type, device_id, outlet, advertisement_link, transaction_time, user_name) FROM stdin;
1	1	1	100	adv	863482064245208	1	1	2024-05-12 18:33:15.237894	\N
2	1	1	\N	\N	\N	1	\N	2024-05-12 18:40:20.09159	\N
3	1	1	\N	\N	\N	1	\N	2024-05-13 14:07:45.492338	\N
4	1	1	100	adv	863482064245208	1	1	2024-05-13 14:42:52.237664	zss
1210650625	3	1	\N	\N	\N	\N	\N	2024-05-14 18:39:24.387655	\N
95010818	3	1	100	\N	\N	\N	\N	2024-05-15 16:59:54.204421	微信用户
95010819	3	1	100	\N	\N	\N	\N	2024-05-15 16:59:54.204409	微信用户
380108801	4	1	\N	\N	\N	\N	\N	2024-05-15 18:18:18.525015	\N
2053636098	4	1	\N	\N	\N	\N	\N	2024-05-15 18:21:59.096599	\N
1491599361	4	1	\N	\N	\N	\N	\N	2024-05-15 18:22:37.864775	\N
1491599362	4	1	\N	\N	\N	\N	\N	2024-05-15 18:22:37.867568	\N
-366477310	3	1	100	\N	\N	\N	\N	2024-05-15 18:26:14.507463	微信用户
-366477309	3	1	100	\N	\N	\N	\N	2024-05-15 18:26:14.509366	微信用户
-1155006462	4	1	100	\N	\N	\N	\N	2024-05-15 18:27:43.414761	微信用户
1919418369	3	1	100	\N	\N	\N	\N	2024-05-15 18:28:38.422328	微信用户
\.


--
-- Data for Name: tb_qrcode; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_qrcode (id, outlet, chaddress, imgname, codeaddress, appid, saler) FROM stdin;
863482064251883	1	\N	\N	\N	91f25fab87eb44c9d5c2494caedc2683	13466647109
863482064245992	1	\N	\N	\N	91f25fab87eb44c9d5c2494caedc2683	13466647109
863482064245059	1	\N	\N	\N	91f25fab87eb44c9d5c2494caedc2683	13466647109
863482064245208	1	\N	\N	\N	91f25fab87eb44c9d5c2494caedc2683	13466647109
863482064246198	1	\N	\N	\N	91f25fab87eb44c9d5c2494caedc2683	13466647109
863482064297746	1	\N	\N	\N	91f25fab87eb44c9d5c2494caedc2683	13466647109
863482064251750	1	\N	\N	\N	91f25fab87eb44c9d5c2494caedc2683	13466647109
863482064297662	1	\N	\N	\N	91f25fab87eb44c9d5c2494caedc2683	13466647109
863482064245075	1	\N	\N	\N	91f25fab87eb44c9d5c2494caedc2683	13466647109
861658064564373	1	hx	91f25fab87eb44c9d5c2494caedc268386165806456437313466647109.jpg	D:/phpstudy_pro/WWW/waterStation/	91f25fab87eb44c9d5c2494caedc2683	13466647109
863482064251818	1	\N	91f25fab87eb44c9d5c2494caedc268386348206425181813466647109.jpg	D:/phpstudy_pro/WWW/waterStation/	91f25fab87eb44c9d5c2494caedc2683	13466647109
\.


--
-- Data for Name: tb_task; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_task (id, taskcounts, createtime) FROM stdin;
\.


--
-- Data for Name: tb_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_user (id, openid, phone, name, pointbalance, profile_photo, task_count) FROM stdin;
2	1	\N	\N	100	\N	\N
1	1	11111111	zs	1400	\N	\N
4	oJvGF64gpc0jzMjroqbWWf_iDdm0	\N	微信用户	500	\N	\N
3	oJvGF61ZN7myiRfBGCyF33rbWf3Y	\N	微信用户	500	\N	\N
\.


--
-- Name: tb_admin_admin_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_admin_admin_id_seq', 13, true);


--
-- Name: tb_pointrules_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_pointrules_id_seq', 1, false);


--
-- Name: tb_pointtransactionrecords_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_pointtransactionrecords_id_seq', 1, false);


--
-- Name: tb_task_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_task_id_seq', 1, false);


--
-- Name: tb_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_user_id_seq', 4, true);


--
-- Name: tb_admin tb_admin_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_admin
    ADD CONSTRAINT tb_admin_pkey PRIMARY KEY (id);


--
-- Name: tb_pointrules tb_pointrules_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_pointrules
    ADD CONSTRAINT tb_pointrules_pkey PRIMARY KEY (id);


--
-- Name: tb_pointrules tb_pointrules_rulesname_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_pointrules
    ADD CONSTRAINT tb_pointrules_rulesname_key UNIQUE (rulesname);


--
-- Name: tb_pointtransactionrecords tb_pointtransactionrecords_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_pointtransactionrecords
    ADD CONSTRAINT tb_pointtransactionrecords_pkey PRIMARY KEY (id);


--
-- Name: tb_qrcode tb_qrcode_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_qrcode
    ADD CONSTRAINT tb_qrcode_pkey PRIMARY KEY (id);


--
-- Name: tb_task tb_task_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_task
    ADD CONSTRAINT tb_task_pkey PRIMARY KEY (id);


--
-- Name: tb_user tb_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_user
    ADD CONSTRAINT tb_user_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

