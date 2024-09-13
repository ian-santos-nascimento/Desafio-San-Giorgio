CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS public.vendedor
(
    codigo_vendedor UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    nome VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS public.cobranca
(
    codigo_cobranca UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    valor DECIMAL(19,2)
);

CREATE TABLE IF NOT EXISTS public.pagamento
(
    codigo_pagamento UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    codigo_cobranca UUID NOT NULL,
    valor DECIMAL(19, 2) NOT NULL,
    status VARCHAR(255) NOT NULL

);

INSERT INTO public.vendedor(codigo_vendedor, nome)
VALUES ('e154ddc7-48a8-4336-9199-73a56137e220', 'Teste');

INSERT INTO public.cobranca(codigo_cobranca, valor)
VALUES ('a1e724f9-f98f-43ad-96b6-3d4c1256b164', 20.50);
