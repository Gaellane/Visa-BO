ALTER TABLE demande ADD COLUMN IF NOT EXISTS numero VARCHAR(255);

WITH demandes_a_numeroter AS (
	SELECT
		id,
		'DM-' || ROW_NUMBER() OVER (ORDER BY id) AS numero
	FROM demande
	WHERE numero IS NULL OR numero = ''
)
UPDATE demande d
SET numero = n.numero
FROM demandes_a_numeroter n
WHERE d.id = n.id;

ALTER TABLE demande ALTER COLUMN numero SET NOT NULL;