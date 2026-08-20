-- Insertion de comptes (Account)
INSERT INTO account (id, account_type) VALUES
                                           ('acc-001', 'STANDARD'::account_type),
                                           ('acc-002', 'PREMIUM'::account_type),
                                           ('acc-003', 'GOLD'::account_type),
                                           ('acc-004', 'STANDARD'::account_type);

-- Insertion de transactions (Transaction)
INSERT INTO transaction (id, created_at, transaction_type, amount, reason, account_id) VALUES
                                                                                           ('tx-001', '2026-01-05 09:15:00', 'IN'::transaction_type,  500000.00, 'Dépôt initial','acc-001'),
                                                                                           ('tx-002', '2026-01-10 14:30:00', 'OUT'::transaction_type, 75000.00,  'Retrait guichet','acc-001'),
                                                                                           ('tx-003', '2026-02-01 08:00:00', 'IN'::transaction_type,  1200000.00,'Virement salaire','acc-002'),
                                                                                           ('tx-004', '2026-02-15 17:45:00', 'OUT'::transaction_type, 300000.00, 'Paiement facture','acc-002'),
                                                                                           ('tx-005', '2026-03-03 11:20:00', 'IN'::transaction_type,  2000000.00,'Dépôt exceptionnel','acc-003'),
                                                                                           ('tx-006', '2026-03-10 10:00:00', 'OUT'::transaction_type, 450000.00, 'Achat en ligne','acc-003'),
                                                                                           ('tx-007', '2026-03-12 16:00:00', 'IN'::transaction_type,  150000.00, 'Remboursement','acc-004'),
                                                                                           ('tx-008', '2026-03-14 09:30:00', 'OUT'::transaction_type, 50000.00,  'Frais bancaires','acc-004');