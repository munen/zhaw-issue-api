-- :name create-issue! :<! :n
-- :doc creates a new issue
INSERT INTO issues
(client_id, done, title, due_date, project_id, project_client_id, priority)
VALUES (:client_id, :done, :title, :due_date, :project_id, :project_client_id, :priority)
returning id

-- :name update-issue! :! :n
-- :doc update an existing issue
UPDATE issues
SET title = :title, done = :done, due_date = :due_date, priority = :priority, updated_at = NOW()
WHERE project_id = :project_id AND id = :id

-- :name get-issue :? :1
-- :doc retrieve a issue given the id.
SELECT * FROM issues
WHERE project_id = :project_id AND id = :id

-- :name get-issues :? :*
-- :doc retrieves all issues
SELECT * FROM issues
WHERE project_id = :project_id
order by id

-- :name delete-issue! :! :n
-- :doc delete a issue given the id
DELETE FROM issues
WHERE id = :id AND project_id = :project_id




-- :name create-project! :<! :n
-- :doc creates a new project
INSERT INTO projects
(client_id, title, active)
VALUES (:client_id, :title, :active)
returning id

-- :name delete-project! :! :n
-- :doc delete a project given the id
DELETE FROM projects
WHERE id = :id

-- :name update-project! :! :n
-- :doc update an existing project
UPDATE projects
SET title = :title, active = :active
WHERE id = :id

-- :name get-project :? :1
-- :doc retrieve a project given the id.
SELECT * FROM projects
WHERE id = :id
