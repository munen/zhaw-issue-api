-- :name create-issue! :! :n
-- :doc creates a new issue
INSERT INTO issues
(id, title)
VALUES (:id, :title)

-- :name update-issue! :! :n
-- :doc update an existing issue
UPDATE issues
SET title = :title
WHERE id = :id

-- :name get-issue :? :1
-- :doc retrieve a issue given the id.
SELECT * FROM issues
WHERE id = :id

-- :name delete-issue! :! :n
-- :doc delete a issue given the id
DELETE FROM users
WHERE id = :id
